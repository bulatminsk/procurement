package by.htp.procurement.initializer;

import by.htp.procurement.entity.Company;
import by.htp.procurement.logic.ServiceException;
import by.htp.procurement.content.parameter.ParamType;
import java.util.EnumMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanyParametersInitializer {

    private static final CompanyParametersInitializer INSTANCE = new CompanyParametersInitializer();
    private static Logger logger = LogManager.getLogger();

    public static CompanyParametersInitializer getInstance() {
        return INSTANCE;
    }

    public void setCompanyParamFromMap(Company company, EnumMap<ParamType, String> companyParameters) throws ServiceException {
        company.setName(companyParameters.get(ParamType.NAME));
        company.setTaxNumber(companyParameters.get(ParamType.TAX_NUMBER));
        company.setWeb(companyParameters.get(ParamType.WEB));
        company.setCountry(companyParameters.get(ParamType.COUNTRY));
    }

    public void setCompanyParamWithNullFromMap(Company company, EnumMap<ParamType, String> companyParameters) throws ServiceException {
        if (companyParameters.get(ParamType.NAME) != null) {
            company.setName(companyParameters.get(ParamType.NAME));
        }
        if (companyParameters.get(ParamType.TAX_NUMBER) != null) {
            company.setTaxNumber(companyParameters.get(ParamType.TAX_NUMBER));
        }
        if (companyParameters.get(ParamType.WEB) != null) {
            company.setWeb(companyParameters.get(ParamType.WEB));
        }
        if (companyParameters.get(ParamType.COUNTRY) != null) {
            company.setCountry(companyParameters.get(ParamType.COUNTRY));
        }
    }
}
