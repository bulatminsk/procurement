package by.htp.procurement.security;

import by.htp.procurement.content.RequestContent;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import by.htp.procurement.logic.ServiceException;
import by.htp.procurement.logic.TenderService;
import static by.htp.procurement.content.attribute.AttributeType.*;
import java.util.Set;

public class CommitteeMemberChecker {

    private static final String PARAM_TENDER_ID = "tender_id";
    private static final CommitteeMemberChecker INSTANCE = new CommitteeMemberChecker();

    public static CommitteeMemberChecker getInstance() {
        return INSTANCE;
    }

    public boolean checkIfMemberOfCommittee(RequestContent content) throws ServiceException {
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        Set<User> committee = new TenderService().findUsersInTenderCommittee(Integer.parseInt(content.getParameter(PARAM_TENDER_ID)));
        return committee.stream().anyMatch(e -> e.getLogin().contentEquals(user.getLogin()));
    }
}
