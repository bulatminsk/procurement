package by.htp.procurement.security;

import by.htp.procurement.content.RequestContent;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import by.htp.procurement.logic.ServiceException;
import by.htp.procurement.logic.TenderService;
import static by.htp.procurement.content.attribute.AttributeType.*;

public class TenderOwnerChecker {

    private static final String PARAM_TENDER_ID="tender_id";
    private static final TenderOwnerChecker INSTANCE = new TenderOwnerChecker();
    

    public static TenderOwnerChecker getInstance() {
        return INSTANCE;
    }

    public boolean checkIfTenderOwner(RequestContent content) throws ServiceException {
        User user = (User) content.getSessionAttribute(USER.getAttribute());
        Tender tender = new TenderService().findTenderById(Integer.parseInt(content.getParameter(PARAM_TENDER_ID)));
        return user.getLogin().equals(tender.getUser().getLogin());
    }
}
