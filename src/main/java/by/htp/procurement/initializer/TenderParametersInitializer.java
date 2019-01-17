package by.htp.procurement.initializer;

import by.htp.procurement.entity.Tender;
import by.htp.procurement.logic.ServiceException;
import by.htp.procurement.content.parameter.ParamType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.EnumMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.htp.procurement.content.constant.ContentConstant.*;

public class TenderParametersInitializer {

    private static final TenderParametersInitializer INSTANCE = new TenderParametersInitializer();
    private static Logger logger = LogManager.getLogger();

    public static TenderParametersInitializer getInstance() {
        return INSTANCE;
    }

    public void setTenderParamFromMap(Tender tender, EnumMap<ParamType, String> tenderParameters) throws ServiceException {
        tender.setName(tenderParameters.get(ParamType.TENDER_NAME));
        tender.setCategory(tenderParameters.get(ParamType.CATEGORY));
        tender.setDescription(tenderParameters.get(ParamType.DESCRIPTION));
        tender.setPrice(Integer.parseInt(tenderParameters.get(ParamType.PRICE)));
        try {
            tender.setDeadlineAt(new SimpleDateFormat(DATE_FORMAT).parse(tenderParameters.get(ParamType.DEADLINE_AT)));
        } catch (ParseException e) {
            throw new ServiceException(e);
        }
    }

    public void setTenderParamWithNullFromMap(Tender tender, EnumMap<ParamType, String> tenderParameters) throws ServiceException {
        if (tenderParameters.get(ParamType.TENDER_NAME) != null) {
            tender.setName(tenderParameters.get(ParamType.TENDER_NAME));
        }
        if (tenderParameters.get(ParamType.CATEGORY) != null) {
            tender.setCategory(tenderParameters.get(ParamType.CATEGORY));
        }
        if (tenderParameters.get(ParamType.DESCRIPTION) != null) {
            tender.setDescription(tenderParameters.get(ParamType.DESCRIPTION));
        }
        if (tenderParameters.get(ParamType.PRICE) != null) {
            tender.setPrice(Integer.parseInt(tenderParameters.get(ParamType.PRICE)));
        }
    }
}
