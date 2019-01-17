package by.htp.procurement.logic;

import by.htp.procurement.initializer.TenderParametersInitializer;
import by.htp.procurement.content.CommandResult;
import by.htp.procurement.content.constant.EntityNameType;
import by.htp.procurement.content.RequestContent;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.dao.impl.TenderDaoImpl;
import by.htp.procurement.entity.User;
import by.htp.procurement.util.MessageManager;
import by.htp.procurement.util.ConfigurationManager;
import by.htp.procurement.content.parameter.ParamType;
import by.htp.procurement.entity.Entity;
import by.htp.procurement.entity.Evaluation;
import by.htp.procurement.validator.ParameterValidator;
import by.htp.procurement.verifier.EntityInvalidStateVerifier;
import by.htp.procurement.reader.InputReader;
import by.htp.procurement.security.TenderOwnerCheck;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.htp.procurement.verifier.EntityInvalidStateTypeCollection.*;
import static by.htp.procurement.content.attribute.AttributeType.*;
import static by.htp.procurement.content.attribute.AttributeToRemoveAfterRedirectType.*;
import static by.htp.procurement.content.parameter.ParamType.*;

public class TenderService {

    private static final TenderService INSTANCE = new TenderService();
    private static Logger logger = LogManager.getLogger();

    public static TenderService getInstance() {
        return INSTANCE;
    }

    /**
     * Creating a new tender on platform. Tender is created if the data entered
     * is valid
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    public CommandResult addTender(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        CommandResult router = new CommandResult(ConfigurationManager.
                getProperty("path.page.listTenderByUser"), CommandResult.ResponseType.REDIRECT);
        String lang = content.getSessionAttribute(LANGUAGE.getAttribute()).toString().toUpperCase();
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        Tender tender = new Tender();
        StringBuilder wrongParameters = new StringBuilder();
        EnumMap<ParamType, String> tenderParameters = InputReader.getInstance().
                readParameters(content, parametersToValidate);
        boolean inputIsValid = ParameterValidator.getInstance().
                validate(tenderParameters, wrongParameters);
        if (inputIsValid) {
            TenderParametersInitializer.getInstance().setTenderParamFromMap(tender, tenderParameters);
            tender.setUser(user);
            try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
                tenderDao.create(tender);
            } catch (DaoException ex) {
                throw new ServiceException(ex);
            }
            List<Tender> tendersByUser = findTendersByUser(user.getLogin());
            content.removeSessionAttribute(TENDER.getAttribute());
            content.setSessionAttribute(USER_TENDERS.getAttribute(), tendersByUser);
            content.setSessionAttribute(SUCCESS_MESSAGE.getAttribute(), MessageManager.
                    valueOf(lang).getMessage("message.tenderAdd.success"));
        } else {
            TenderParametersInitializer.getInstance().setTenderParamWithNullFromMap(tender, tenderParameters);
            content.setAttribute(TENDER.getAttribute(), tender);
            content.setAttribute(FAILURE_MESSAGE.getAttribute(), MessageManager.valueOf(lang).
                    getMessage("message.invalid.parameter") + wrongParameters.toString());
            router.setPage(ConfigurationManager.getProperty("path.page.addTender"));
            router.setResponseType(CommandResult.ResponseType.FORWARD);
        }
        return router;
    }

    /**
     * Choosing the tender to apply. Form to place proposal will be opened if
     * tender deadline is not over, tender is not deleted, applied user is
     * registered company User, who placed proposal is proposal owner
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    public CommandResult applyTender(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.addProposal");
        String lang = content.getSessionAttribute(LANGUAGE.getAttribute()).toString().toUpperCase();
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = findTenderById(tenderId);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.TENDER, tender);
        entites.put(EntityNameType.USER, user);
        StringBuilder stateMessage = new StringBuilder();
        boolean tenderState = EntityInvalidStateVerifier.getInstance().
                verify(entites, stateForApplyTender, stateMessage, lang);
        if (tenderState) {
            content.setSessionAttribute(TENDER.getAttribute(), tender);
        } else {
            page = ConfigurationManager.getProperty("path.page.main");
            content.setAttribute(INVALID_STATE.getAttribute(), stateMessage);
        }
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    /**
     * Choice to place a tender on platform. Form to place tender will be opened
     * if user is registered company User, who placed tender is tender owner
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    public CommandResult placeTender(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        String page;
        String lang = (String) (content.getSessionAttribute(LANGUAGE.getAttribute()));
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.USER, user);
        StringBuilder stateMessage = new StringBuilder();
        boolean tenderState = EntityInvalidStateVerifier.getInstance().
                verify(entites, stateForPlaceTender, stateMessage, lang);
        if (tenderState) {
            page = ConfigurationManager.getProperty("path.page.addTender");
        } else {
            page = ConfigurationManager.getProperty("path.page.main");
            content.setAttribute(INVALID_STATE.getAttribute(), stateMessage);
        }
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    /**
     * Choice to move to an active (recently selected) tender.
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    public CommandResult goTender(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.activeTender");
        Tender tender = content.getParameter(TENDER_ID.getName()).isEmpty() ? null
                : findTenderById(Integer.parseInt(content.getParameter(TENDER_ID.getName())));
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    /**
     * Choice to publish tender and made it visible to other users. Tender will
     * be published if the user is the tender owner, tender is not yet published
     * or deleted and evaluation criteria for tender are defined
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    @TenderOwnerCheck
    public CommandResult publishTender(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        CommandResult router = new CommandResult(ConfigurationManager.
                getProperty("path.page.activeTender"), CommandResult.ResponseType.REDIRECT);
        String lang = (String) (content.getSessionAttribute(LANGUAGE.getAttribute()));
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = findTenderById(tenderId);
        Set<Evaluation> criteria = new EvaluationService().findEvaluationsByTender(tenderId);
        tender.setCriteria(criteria);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.TENDER, tender);
        StringBuilder stateMessage = new StringBuilder();
        boolean tenderState = EntityInvalidStateVerifier.getInstance().
                verify(entites, stateForPublishTender, stateMessage, lang);
        if (tenderState) {
            tender.setPublishedAt(new Date());
            try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
                tenderDao.update(tender);
            } catch (DaoException ex) {
                throw new ServiceException(ex);
            }
            content.setSessionAttribute(TENDER.getAttribute(), tender);
        } else {
            router.setResponseType(CommandResult.ResponseType.FORWARD);
            content.setAttribute(INVALID_STATE.getAttribute(), stateMessage);
        }
        return router;
    }

    /**
     * Choice to delete tender and made it non visible to other users. Tender
     * will be deleted if the user is the tender owner
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    @TenderOwnerCheck
    public CommandResult deleteTender(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.listTenderByUser");
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = new Tender();
        tender.setId(tenderId);
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            tenderDao.delete(tender);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        List<Tender> tendersByUser = findTendersByUser(user.getLogin());
        content.removeSessionAttribute(TENDER.getAttribute());
        content.setSessionAttribute(USER_TENDERS.getAttribute(), tendersByUser);
        return new CommandResult(page, CommandResult.ResponseType.REDIRECT);
    }

    /**
     * Choice of tender owner to add other user, which represents the same
     * company to the tender committee. The tender committee members will made
     * evaluations of all proposals received to tender.
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    @TenderOwnerCheck
    public CommandResult addToTenderCommittee(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String login = content.getParameter(MEMBER_TO_ADD_LOGIN.getName());
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = new Tender();
        tender.setId(tenderId);
        User user = new User();
        user.setLogin(login);
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            tenderDao.addUserToTenderCommittee(tender, user);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        return appointTenderCommittee(content, parametersToValidate);
    }

    /**
     * Choice of tender owner to appoint a tender committee. The tender
     * committee members will made evaluations of all proposals received to
     * tender. In the tender committee could be appointed all users, which
     * represent the same company as tender owner
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    @TenderOwnerCheck
    public CommandResult appointTenderCommittee(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.addTenderCommittee");
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Integer companyId = user.getCompany().getId();
        Tender tender = findTenderById(tenderId);
        Set<User> usersInTenderCommittee = findUsersInTenderCommittee(tenderId);
        Set<User> usersNotInTenderCommittee = new UserService().
                findUsersNotInTenderCommitteeByCompanyId(tenderId, companyId);
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        content.setSessionAttribute(TENDER_APPOINTED_USERS.getAttribute(), usersInTenderCommittee);
        content.setSessionAttribute(COMPANY_USERS.getAttribute(), usersNotInTenderCommittee);
        return new CommandResult(page, CommandResult.ResponseType.REDIRECT);
    }

    /**
     * Choice of tender owner to remove user from the tender committee.
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    @TenderOwnerCheck
    public CommandResult deleteFromTenderCommittee(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String login = content.getParameter(MEMBER_TO_DELETE_LOGIN.getName());
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = new Tender();
        tender.setId(tenderId);
        User user = new User();
        user.setLogin(login);
        deleteUserFromTenderCommittee(tender, user);
        return appointTenderCommittee(content, parametersToValidate);
    }

    /**
     * Find a list of published active tenders
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    public CommandResult findTendersPublishedAndNotArchived(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.listTender");
        List<Tender> tendersPublishedAndNotArchived;
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            tendersPublishedAndNotArchived = tenderDao.findTendersPublishedAndNotArchived();
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        content.setSessionAttribute(TENDERS.getAttribute(), tendersPublishedAndNotArchived);
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    /**
     * Find a list of tenders assigned to tender committee member to evaluate
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    public CommandResult findTendersAssignedToTCMember(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.listTenderByCommittee");
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        List<Tender> committeeMemberTenders;
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            committeeMemberTenders = tenderDao.findTendersAssignedToTCMember(user.getLogin());
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        content.setSessionAttribute(COMMITTEE_MEMBER_TENDERS.getAttribute(), committeeMemberTenders);
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    /**
     * Find a list of tenders, placed by User
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    public CommandResult findTendersByUser(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.listTenderByUser");
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        List<Tender> tendersByUser = findTendersByUser(user.getLogin());
        content.setSessionAttribute(USER_TENDERS.getAttribute(), tendersByUser);
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    /**
     * Find a tender by id
     *
     * @return Tender
     * @throws ServiceException if DaoException
     */
    public Tender findTenderById(Integer id) throws ServiceException {
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            return tenderDao.findEntityById(id);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Find a list of tenders, placed by User
     *
     * @return List<Tender>
     * @throws ServiceException if DaoException
     */
    public List<Tender> findTendersByUser(String login) throws ServiceException {
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            return tenderDao.findTendersByUser(login);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Find a list of published active tenders
     *
     * @return List<Tender>
     * @throws ServiceException if DaoException
     */
    public List<Tender> findPublishedActiveTendersByUser(String login) throws ServiceException {
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            return tenderDao.findPublishedActiveTendersByUser(login);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Remove the user from the tender committee
     *
     * @return void
     * @throws ServiceException if DaoException
     */
    public void deleteUserFromTenderCommittee(Tender tender, User user) throws ServiceException {
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            tenderDao.deleteUserFromTenderCommittee(tender, user);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Find a list of Users which are appointed to tender committee
     *
     * @return Set<User>
     * @throws ServiceException if DaoException
     */
    public Set<User> findUsersInTenderCommittee(Integer id) throws ServiceException {
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            return tenderDao.findUsersInTenderCommittee(id);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Set the winner of the tender
     *
     * @return void
     * @throws ServiceException if DaoException
     */
    public void setWinner(Tender tender) throws ServiceException {
        try (TenderDaoImpl tenderDao = new TenderDaoImpl()) {
            tenderDao.setWinner(tender);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }
}
