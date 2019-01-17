package by.htp.procurement.command;

import by.htp.procurement.content.parameter.ParamType;
import by.htp.procurement.logic.CommonService;
import by.htp.procurement.logic.EvaluationService;
import by.htp.procurement.logic.ProposalService;
import by.htp.procurement.logic.TenderService;
import by.htp.procurement.logic.UserService;
import java.util.EnumSet;
import static by.htp.procurement.validator.ParamTypeCollection.*;

public enum CommandType {

    /*COMMON*/
    LOGIN(UserService.getInstance()::login, loginParameters),
    LOGOUT(CommonService.getInstance()::logout),
    LOCALE(CommonService.getInstance()::defineLocale),
    REGISTER(UserService.getInstance()::register, loginParameters),
    ADD_COMPANY(CommonService.getInstance()::addCompany, companyParameters),
    GO_COMPANY(CommonService.getInstance()::goCompany),
    SEARCH_COMPANY(CommonService.getInstance()::searchCompany),
    /*USER*/
    UPDATE_PROFILE(UserService.getInstance()::updateProfile, userParameters),
    SHOW_PROFILE(UserService.getInstance()::showProfile),
    /*TENDER*/
    ADD_TENDER(TenderService.getInstance()::addTender, tenderParameters),
    ADD_TO_COMMITTEE(TenderService.getInstance()::addToTenderCommittee),
    APPOINT_TENDER_COMMITTEE(TenderService.getInstance()::appointTenderCommittee),
    APPLY_TENDER(TenderService.getInstance()::applyTender),
    GO_TENDER(TenderService.getInstance()::goTender),
    DELETE_TENDER(TenderService.getInstance()::deleteTender),
    PLACE_TENDER(TenderService.getInstance()::placeTender),
    PUBLISH_TENDER(TenderService.getInstance()::publishTender),
    DELETE_FROM_COMMITTEE(TenderService.getInstance()::deleteFromTenderCommittee),
    FIND_TENDERS_PUBLISHED_AND_NOT_ARCH(TenderService.getInstance()::findTendersPublishedAndNotArchived),
    FIND_USER_TENDERS(TenderService.getInstance()::findTendersByUser),
    FIND_TENDERS_BY_TC_MEMBER(TenderService.getInstance()::findTendersAssignedToTCMember),
    /*PROPOSAL*/
    ADD_PROPOSAL(ProposalService.getInstance()::addProposal, proposalParameters),
    ADD_PROPOSAL_COMMENT(ProposalService.getInstance()::addProposalComment),
    FIND_PROPOSALS_BY_TENDER(ProposalService.getInstance()::findProposalsByTender),
    FIND_USER_PROPOSAL(ProposalService.getInstance()::findProposalsByUser),
    DELETE_FILE_PROPOSAL(ProposalService.getInstance()::deleteFileProposal),
    GO_PROPOSAL(ProposalService.getInstance()::goProposal),
    /*EVALUATION*/
    DEFINE_CRITERIA(EvaluationService.getInstance()::defineCriteria),
    DELETE_CRITERIA(EvaluationService.getInstance()::deleteCriteria),
    ADD_EVALUATION(EvaluationService.getInstance()::addEvaluation,evaluationParameters),
    ADD_PROPOSAL_EVALUATION_BY_USER(EvaluationService.getInstance()::addProposalEvaluationByUser),
    FIND_EVALUATION_BY_USER_ASSIGNED(EvaluationService.getInstance()::findEvaluationByUserAssigned),
    FIND_ALL_PROPOSAL_EVALUATIONS(EvaluationService.getInstance()::findAllProposalEvaluations),
    UPDATE_PROPOSAL_EVALUATION_BY_USER(EvaluationService.getInstance()::updateProposalEvaluationByUser),
    CHOOSE_WINNER(EvaluationService.getInstance()::chooseWinner),
    FIND_TENDER_FINAL_SCORE(EvaluationService.getInstance()::findTenderFinalScore);

    private ActionCommand command;
    private EnumSet<ParamType> parametersSet;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    CommandType(ActionCommand command, EnumSet<ParamType> parametersSet) {
        this.command = command;
        this.parametersSet = parametersSet;
    }

    public ActionCommand getActionCommand() {
        return command;
    }

    public EnumSet<ParamType> getParametersSet() {
        return parametersSet;
    }

}
