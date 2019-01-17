package by.htp.procurement.logic;

import by.htp.procurement.content.CommandResult;
import by.htp.procurement.content.RequestContent;
import by.htp.procurement.content.constant.EntityNameType;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.dao.impl.ProposalDaoImpl;
import by.htp.procurement.entity.Entity;
import by.htp.procurement.entity.Proposal;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import by.htp.procurement.util.MessageManager;
import by.htp.procurement.util.ConfigurationManager;
import by.htp.procurement.content.parameter.ParamType;
import by.htp.procurement.reader.InputReader;
import by.htp.procurement.validator.ParameterValidator;
import by.htp.procurement.verifier.EntityInvalidStateVerifier;
import by.htp.procurement.security.CommitteeMemberCheck;
import java.util.Date;
import java.util.List;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.htp.procurement.verifier.EntityInvalidStateTypeCollection.*;
import static by.htp.procurement.content.attribute.AttributeType.*;
import static by.htp.procurement.content.attribute.AttributeToRemoveAfterRedirectType.*;
import static by.htp.procurement.content.parameter.ParamType.*;

public class ProposalService {

    private static final ProposalService INSTANCE = new ProposalService();
    private static Logger logger = LogManager.getLogger();

    public static ProposalService getInstance() {
        return INSTANCE;
    }

    public CommandResult addProposal(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        CommandResult router = new CommandResult(ConfigurationManager.
                getProperty("path.page.addProposal"), CommandResult.ResponseType.FORWARD);
        String lang = content.getSessionAttribute(LANGUAGE.getAttribute()).toString().toUpperCase();
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        Tender tender = (Tender) content.getSessionAttribute(TENDER.getAttribute());
        tender = new TenderService().findTenderById(tender.getId());
        Proposal proposal = new Proposal();
        Date applied = new Date();
        StringBuilder wrongParameters = new StringBuilder();
        EnumMap<ParamType, String> proposalParameters = InputReader.getInstance().
                readAttributes(content, parametersToValidate);
        boolean inputIsValid = ParameterValidator.getInstance().
                validate(proposalParameters, wrongParameters);
        if (!inputIsValid) {
            content.setAttribute(FAILURE_MESSAGE.getAttribute(), MessageManager.
                    valueOf(lang).getMessage("message.invalid.parameter") + wrongParameters.toString());
            return router;
        }
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.TENDER, tender);
        StringBuilder stateMessage = new StringBuilder();
        boolean proposalState = EntityInvalidStateVerifier.getInstance().
                verify(entites, stateForAddProposal, stateMessage, lang);
        if (proposalState) {
            proposal.setAppliedAt(applied);
            proposal.setApplication(proposalParameters.get(ParamType.APPLICATION));
            proposal.setFilePath(proposalParameters.get(ParamType.FILE_SOURCE));
            proposal.setTender(tender);
            proposal.setUser(user);
            create(proposal);
            content.removeSessionAttribute(APPLICATION.getName());
            content.removeSessionAttribute(FILE_SOURCE.getName());
            List<Proposal> proposalsByUser = findProposalsByUser(user.getLogin());
            content.setSessionAttribute(USER_PROPOSALS.getAttribute(), proposalsByUser);
            content.setSessionAttribute(SUCCESS_MESSAGE.getAttribute(), MessageManager.
                    valueOf(lang).getMessage("message.proposalAdd.success"));
            content.setSessionAttribute(TENDER.getAttribute(), tender);
            router.setPage(ConfigurationManager.getProperty("path.page.listUserProposal"));
            router.setResponseType(CommandResult.ResponseType.REDIRECT);
        } else {
            content.setAttribute(INVALID_STATE.getAttribute(), stateMessage);
        }
        return router;
    }

    public CommandResult addProposalComment(RequestContent content,
            EnumSet<ParamType> parametersToValidate) {
        String proposalDescription = content.getParameter(APPLICATION.getName());
        content.setSessionAttribute(APPLICATION.getName(), proposalDescription);
        String page = ConfigurationManager.getProperty("path.page.addProposal");
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    public CommandResult deleteFileProposal(RequestContent content,
            EnumSet<ParamType> parametersToValidate) {
        content.removeSessionAttribute(FILE_SOURCE.getName());
        String page = ConfigurationManager.getProperty("path.page.addProposal");
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    public CommandResult goProposal(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.activeProposal");
        Proposal proposal = content.getParameter(PROPOSAL_ID.getName()).isEmpty()
                ? null : findProposalById(Integer.parseInt(content.getParameter(PROPOSAL_ID.getName())));
        content.setSessionAttribute(PROPOSAL.getAttribute(), proposal);
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    @CommitteeMemberCheck
    public CommandResult findProposalsByTender(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.listTenderProposal");
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Set<Proposal> proposalsByTender = findProposalsByTender(tenderId);
        Tender tender = new TenderService().findTenderById(tenderId);
        content.setSessionAttribute(TENDER_PROPOSALS.getAttribute(), proposalsByTender);
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        return router;
    }

    public CommandResult findProposalsByUser(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.listUserProposal");
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        List<Proposal> proposalsByUser = findProposalsByUser(user.getLogin());
        content.setSessionAttribute(USER_PROPOSALS.getAttribute(), proposalsByUser);
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        return router;
    }

    public void create(Proposal proposal) throws ServiceException {
        try (ProposalDaoImpl proposalDao = new ProposalDaoImpl()) {
            proposalDao.create(proposal);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public Proposal findProposalById(Integer proposalId) throws ServiceException {
        try (ProposalDaoImpl proposalDao = new ProposalDaoImpl()) {
            return proposalDao.findEntityById(proposalId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public Set<Proposal> findProposalsByTender(Integer tenderId) throws ServiceException {
        try (ProposalDaoImpl proposalDao = new ProposalDaoImpl()) {
            return proposalDao.findProposalsByTender(tenderId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Proposal> findProposalsByUser(String login) throws ServiceException {
        try (ProposalDaoImpl proposalDao = new ProposalDaoImpl()) {
            return proposalDao.findProposalsByUser(login);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }
}
