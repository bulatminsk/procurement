package by.htp.procurement.validator;

import by.htp.procurement.content.parameter.ParamType;
import java.util.EnumSet;

public class ParamTypeCollection {

    public static final EnumSet<ParamType> userParameters = EnumSet.range(ParamType.PASSWORD, ParamType.COMPANY_ID);
    public static final EnumSet<ParamType> companyParameters = EnumSet.range(ParamType.NAME, ParamType.WEB);
    public static final EnumSet<ParamType> loginParameters = EnumSet.range(ParamType.LOGIN, ParamType.PASSWORD);
    public static final EnumSet<ParamType> tenderParameters = EnumSet.range(ParamType.TENDER_NAME, ParamType.DEADLINE_AT);
    public static final EnumSet<ParamType> proposalParameters = EnumSet.range(ParamType.APPLICATION, ParamType.FILE_SOURCE);
    public static final EnumSet<ParamType> evaluationParameters = EnumSet.range(ParamType.CRITERIA, ParamType.TENDER_ID);

}
