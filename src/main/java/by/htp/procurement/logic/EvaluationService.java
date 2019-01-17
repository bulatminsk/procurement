package by.htp.procurement.logic;

import by.htp.procurement.content.CommandResult;
import by.htp.procurement.content.RequestContent;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.dao.impl.EvaluationDaoImpl;
import by.htp.procurement.entity.Evaluation;
import by.htp.procurement.entity.Proposal;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import by.htp.procurement.util.ConfigurationManager;
import by.htp.procurement.content.parameter.ParamType;
import by.htp.procurement.entity.Company;
import by.htp.procurement.initializer.EvaluationParametersInitializer;
import by.htp.procurement.security.CommitteeMemberCheck;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.htp.procurement.security.TenderOwnerCheck;
import by.htp.procurement.util.MessageManager;
import by.htp.procurement.reader.InputReader;
import by.htp.procurement.validator.ParameterValidator;
import java.util.EnumMap;
import static by.htp.procurement.content.attribute.AttributeType.*;
import static by.htp.procurement.content.constant.ContentConstant.*;
import static by.htp.procurement.content.parameter.ParamType.*;

public class EvaluationService {

    private static final EvaluationService INSTANCE = new EvaluationService();

    private static Logger logger = LogManager.getLogger();

    public static EvaluationService getInstance() {
        return INSTANCE;
    }

    @TenderOwnerCheck
    public CommandResult addEvaluation(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.addCriteria");
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        String lang = content.getSessionAttribute(LANGUAGE.getAttribute()).toString().toUpperCase();
        Evaluation evaluation = null;
        StringBuilder wrongParameters = new StringBuilder();
        EnumMap<ParamType, String> evaluationParameters = InputReader.getInstance().
                readParameters(content, parametersToValidate);
        boolean inputIsValid = ParameterValidator.getInstance().
                validate(evaluationParameters, wrongParameters);
        if (!inputIsValid) {
            content.setAttribute(FAILURE_MESSAGE.getAttribute(), MessageManager.
                    valueOf(lang).getMessage("message.invalid.parameter") + wrongParameters.toString());
        } else {
            evaluation = new Evaluation();
            EvaluationParametersInitializer.getInstance().
                    setEvaluationParamFromMap(evaluation, evaluationParameters);
            create(evaluation);
        }
        Integer tenderId = Integer.parseInt(evaluationParameters.get(ParamType.TENDER_ID));
        Tender tender = new TenderService().findTenderById(tenderId);
        Set<Evaluation> evaluationsByTender = findEvaluationsByTender(tenderId);
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        content.setAttribute(CRITERIA.getName(), evaluationsByTender);
        return router;
    }

    @TenderOwnerCheck
    public CommandResult chooseWinner(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        Integer winner_id = Integer.parseInt(content.getParameter(WINNER_CHOISE.getName()));
        Company winner = new CommonService().findCompanyById(winner_id);
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = new Tender();
        tender.setId(tenderId);
        tender.setWinner(winner);
        new TenderService().setWinner(tender);
        String page = ConfigurationManager.getProperty("path.page.winner");
        content.setAttribute(COMPANY.getAttribute(), winner);
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        return router;
    }

    @TenderOwnerCheck
    public CommandResult defineCriteria(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.addCriteria");
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = new TenderService().findTenderById(tenderId);
        Set<Evaluation> evaluationsByTender = findEvaluationsByTender(tenderId);
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        content.setAttribute(CRITERIA.getName(), evaluationsByTender);
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    @TenderOwnerCheck
    public CommandResult deleteCriteria(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.addCriteria");
        Integer evaluationId = Integer.parseInt(content.getParameter(CRITERIA_TO_DELETE_ID.getName()));
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = new Tender();
        tender.setId(tenderId);
        Evaluation evaluation = new Evaluation();
        evaluation.setId(evaluationId);
        evaluation.setTender(tender);
        delete(evaluation);
        tender = new TenderService().findTenderById(tenderId);
        Set<Evaluation> evaluationsByTender = findEvaluationsByTender(tenderId);
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        content.setAttribute(CRITERIA.getName(), evaluationsByTender);
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    @CommitteeMemberCheck
    public CommandResult addProposalEvaluationByUser(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.proposalEvaluationUpdate");
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Integer proposalId = Integer.parseInt(content.getParameter(PROPOSAL_ID.getName()));
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        String login = user.getLogin();
        Map<Evaluation, Integer> evaluations = new TreeMap<>();
        Map<Evaluation, Integer> evaluationsAssignedToUser
                = findEvaluationsAssignedToUser(evaluations, login, tenderId, proposalId);
        Tender tender = new TenderService().findTenderById(tenderId);
        Proposal proposal = new ProposalService().findProposalById(proposalId);
        content.setAttribute(EVALUATIONS.getAttribute(), evaluationsAssignedToUser);
        content.setAttribute(TENDER.getAttribute(), tender);
        content.setAttribute(PROPOSAL.getAttribute(), proposal);
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        return router;
    }

    /**
     * Setting scores by the tender committee member against evaluation
     * criteria. The score should be less then max score defined in Evaluation
     * by the tender owner
     *
     * @return CommandResult
     * @throws ServiceException if DaoException
     */
    @CommitteeMemberCheck
    public CommandResult updateProposalEvaluationByUser(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Integer proposalId = Integer.parseInt(content.getParameter(PROPOSAL_ID.getName()));
        Proposal proposal = new Proposal();
        proposal.setId(proposalId);
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        String login = user.getLogin();
        String scores[] = content.getParameterValues(SCORE.getName());
        String evaluationIds[] = content.getParameterValues(EVALUATION_ID.getName());
        if (evaluationIds != null) {
            Map<Integer, Integer> evaluationScoreMap = null;
            try {
                evaluationScoreMap
                        = prepareEvaluationScoreMap(scores, evaluationIds, tenderId);
                updateProposalEvaluationByUser(proposalId, login, evaluationScoreMap);
            } catch (ScoreFormatException ex) {
                logger.error("Scores do not follow established rules",ex);
            }
        }
        String page = ConfigurationManager.getProperty("path.page.proposalEvaluationUpdate");
        Map<Evaluation, Integer> evaluations = new TreeMap<>();
        Map<Evaluation, Integer> evaluationsAssignedToUser
                = findEvaluationsAssignedToUser(evaluations, login, tenderId, proposalId);
        Tender tender = new TenderService().findTenderById(tenderId);
        proposal = new ProposalService().findProposalById(proposalId);
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        content.setSessionAttribute(PROPOSAL.getAttribute(), proposal);
        content.setAttribute(EVALUATIONS.getAttribute(), evaluationsAssignedToUser);
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        return router;
    }

    @CommitteeMemberCheck
    public CommandResult findEvaluationByUserAssigned(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.proposalEvaluationUpdate");
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Integer proposalId = Integer.parseInt(content.getParameter(PROPOSAL_ID.getName()));
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        String login = user.getLogin();
        Map<Evaluation, Integer> evaluations = new TreeMap<>();
        Map<Evaluation, Integer> evaluationsAssignedToUser
                = findEvaluationsAssignedToUser(evaluations, login, tenderId, proposalId);
        content.setAttribute(EVALUATIONS.getAttribute(), evaluationsAssignedToUser);
        Tender tender = new TenderService().findTenderById(tenderId);
        Proposal proposal = new ProposalService().findProposalById(proposalId);
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        content.setSessionAttribute(PROPOSAL.getAttribute(), proposal);
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        return router;
    }

    @TenderOwnerCheck
    public CommandResult findAllProposalEvaluations(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.proposalPUEs");
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Integer proposalId = Integer.parseInt(content.getParameter(PROPOSAL_ID.getName()));
        Map<User, Map<Evaluation, Integer>> prepareProposalUserEvaluations
                = prepareProposalUserEvaluationsMap(tenderId);
        Tender tender = new TenderService().findTenderById(tenderId);
        Proposal proposal = new ProposalService().findProposalById(proposalId);
        Map<User, Map<Evaluation, Integer>> proposalUserEvaluations
                = findProposalUserEvaluations(prepareProposalUserEvaluations, tenderId, proposalId);
        content.setAttribute(PUES.getAttribute(), proposalUserEvaluations);
        content.setSessionAttribute(TENDER.getAttribute(), tender);
        content.setSessionAttribute(PROPOSAL.getAttribute(), proposal);
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        return router;
    }

    @TenderOwnerCheck
    public CommandResult findTenderFinalScore(RequestContent content,
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.tenderScores");
        Integer tenderId = Integer.parseInt(content.getParameter(TENDER_ID.getName()));
        Tender tender = new TenderService().findTenderById(tenderId);
        Company winner = tender.getWinner();
        if (winner == null) {
            HashMap<Proposal, Integer> scores = new HashMap<>();
            Set<Proposal> proposals = new HashSet<>();
            proposals.addAll(new ProposalService().findProposalsByTender(tenderId));
            Iterator iterator = proposals.iterator();
            while (iterator.hasNext()) {
                scores.put((Proposal) iterator.next(), 0);
            }
            HashMap<Proposal, Integer> finalScores = findFinalScoreForTender(scores);
            content.setSessionAttribute(TENDER.getAttribute(), tender);
            content.setAttribute(FINAL_SCORES.getAttribute(), finalScores);

        } else {
            page = ConfigurationManager.getProperty("path.page.winner");
            winner = new CommonService().findCompanyById(winner.getId());
            content.setAttribute(COMPANY.getAttribute(), winner);
        }
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        return router;
    }

    public void create(Evaluation entity) throws ServiceException {
        try (EvaluationDaoImpl evaluationDao = new EvaluationDaoImpl()) {
            evaluationDao.create(entity);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public void delete(Evaluation evaluation) throws ServiceException {
        try (EvaluationDaoImpl evaluationDao = new EvaluationDaoImpl()) {
            evaluationDao.delete(evaluation);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public Set<Evaluation> findEvaluationsByTender(Integer tenderId) throws ServiceException {
        try (EvaluationDaoImpl evaluationDao = new EvaluationDaoImpl()) {
            return evaluationDao.findEvaluationsByTender(tenderId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public Map<Evaluation, Integer> findEvaluationsAssignedToUser(Map<Evaluation, Integer> evaluations, String login, Integer tenderId, Integer proposalId) throws ServiceException {
        try (EvaluationDaoImpl evaluationDao = new EvaluationDaoImpl()) {
            return evaluationDao.findEvaluationsAssignedToUser(evaluations, login, tenderId, proposalId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public Map<User, Map<Evaluation, Integer>> findProposalUserEvaluations(
            Map<User, Map<Evaluation, Integer>> pues, Integer tenderId, Integer proposalId) throws ServiceException {
        try (EvaluationDaoImpl evaluationDao = new EvaluationDaoImpl()) {
            return evaluationDao.findProposalUserEvaluations(pues, proposalId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public HashMap<Proposal, Integer> findFinalScoreForTender(
            HashMap<Proposal, Integer> scores) throws ServiceException {
        try (EvaluationDaoImpl evaluationDao = new EvaluationDaoImpl()) {
            for (Proposal proposal : scores.keySet()) {
                scores.put(proposal, evaluationDao.countFinalScoreForProposal(proposal.getId()));
            }
            return scores;
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public Map<User, Map<Evaluation, Integer>> prepareProposalUserEvaluationsMap(
            Integer tenderId) throws ServiceException {
        Map<User, Map<Evaluation, Integer>> proposalUserEvaluations = new TreeMap<>(
                (User o1, User o2) -> o1.getLogin().compareTo(o2.getLogin()));
        Set<User> users = new TreeSet<>();
        users.addAll(new TenderService().findUsersInTenderCommittee(tenderId));
        users.forEach((e) -> {
            proposalUserEvaluations.put(e, null);
        });
        return proposalUserEvaluations;
    }

    public Map<Evaluation, Integer> addProposalEvaluationByUser(Map<Evaluation, Integer> evaluations, User user, Integer tenderId, Integer proposalId) throws ServiceException {
        Proposal proposal = new Proposal();
        proposal.setId(proposalId);
        String login = user.getLogin();
        findEvaluationsAssignedToUser(evaluations, login, tenderId, proposalId);
        return evaluations;
    }

    public Map<Integer, Integer> prepareEvaluationScoreMap(String[] scores, String[] evaluationIds,
            Integer tenderId) throws ServiceException, ScoreFormatException {
        Set<Evaluation> criteria = findEvaluationsByTender(tenderId);
        Map<Integer, Integer> scoresToUpdate = new HashMap<>();
        List<Evaluation> list_criteria = new ArrayList(criteria);
        for (int i = 0; i < scores.length; i++) {
            Integer score = Integer.parseInt(scores[i]);
            Integer evaluationId = Integer.parseInt(evaluationIds[i]);
            if (Objects.equals(list_criteria.get(i).getId(), evaluationId)
                    && score > SCORE_MIN && score < list_criteria.get(i).getMaxScore()) {
                scoresToUpdate.put(evaluationId, score);
            } else {
                throw new ScoreFormatException();
            }
        }
        return scoresToUpdate;
    }

    public void updateProposalEvaluationByUser(Integer proposalId, String login,
            Map<Integer, Integer> scoresToUpdate) throws ServiceException {
        try (EvaluationDaoImpl evaluationDao = new EvaluationDaoImpl()) {
            evaluationDao.updateProposalEvaluationByUser(proposalId, login, scoresToUpdate);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }
}
