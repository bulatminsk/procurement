package by.htp.procurement.logic;

import by.htp.procurement.content.CommandResult;
import by.htp.procurement.content.RequestContent;
import by.htp.procurement.util.ConfigurationManager;
import by.htp.procurement.content.parameter.ParamType;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.dao.impl.CompanyDaoImpl;
import by.htp.procurement.entity.Company;
import by.htp.procurement.initializer.CompanyParametersInitializer;
import by.htp.procurement.util.MessageManager;
import by.htp.procurement.reader.InputReader;
import by.htp.procurement.validator.ParameterValidator;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.htp.procurement.content.attribute.AttributeType.*;
import static by.htp.procurement.content.attribute.AttributeToRemoveAfterRedirectType.*;
import static by.htp.procurement.content.constant.ContentConstant.*;

public class CommonService {

    private static final CommonService INSTANCE = new CommonService();
    private static Logger logger = LogManager.getLogger();

    public static CommonService getInstance() {
        return INSTANCE;
    }

    public CommandResult defineLocale(RequestContent content, 
            EnumSet<ParamType> parametersToValidate) {
        String language = content.getParameter(LANGUAGE.getAttribute());
        if (language != null) {
            content.setSessionAttribute(LANGUAGE.getAttribute(), language);
        }
        String locale = defineLocale(language);
        content.setSessionAttribute(LOCALE.getAttribute(), locale);
        String pagePath = content.getSessionAttribute(LAST_PAGE.getAttribute()).toString();
        return new CommandResult(pagePath, CommandResult.ResponseType.REDIRECT);
    }

    public CommandResult logout(RequestContent content, 
            EnumSet<ParamType> parametersToValidate) {
        content.invalidateSession();
        return new CommandResult(ConfigurationManager.getProperty("path.page.index"), 
                CommandResult.ResponseType.FORWARD);
    }

    private String defineLocale(String language) {
        String locale;
        if (language.equalsIgnoreCase(RU_LANGUAGE.getAttribute())) {
            locale = RU_LOCALE.getAttribute();
        } else {
            locale = EN_LOCALE.getAttribute();
        }
        return locale;
    }

    public CommandResult addCompany(RequestContent content, 
            EnumSet<ParamType> parametersToValidate) throws ServiceException {
        String page = ConfigurationManager.getProperty("path.page.addCompany");
        CommandResult router = new CommandResult(page, CommandResult.ResponseType.FORWARD);
        String lang = content.getSessionAttribute(LANGUAGE.getAttribute()).toString().toUpperCase();
        Company company = new Company();
        StringBuilder wrongParameters = new StringBuilder();
        EnumMap<ParamType, String> companyParameters = InputReader.getInstance().
                readParameters(content, parametersToValidate);
        boolean inputIsValid = ParameterValidator.getInstance().
                validate(companyParameters, wrongParameters);
        if (inputIsValid) {
            CompanyParametersInitializer.getInstance().
                    setCompanyParamFromMap(company, companyParameters);
            Company companyToCheck = findCompanyByTaxNumberAndCountry(
                    company.getTaxNumber(), company.getCountry());
            if (companyToCheck == null) {
                createCompany(company);
                company=findCompanyByTaxNumberAndCountry(company.getTaxNumber(), 
                        company.getCountry());              
                content.setSessionAttribute(COMPANY_ADDED.getAttribute(), company);
            }
            router.setPage(ConfigurationManager.getProperty("path.page.profile"));
            router.setResponseType(CommandResult.ResponseType.REDIRECT);
        } else {
            CompanyParametersInitializer.getInstance().
                    setCompanyParamWithNullFromMap(company, companyParameters);
            content.setAttribute(COMPANY.getAttribute(), company);
            content.setAttribute(FAILURE_MESSAGE.getAttribute(), MessageManager.
                    valueOf(lang).getMessage("message.invalid.parameter") + 
                    wrongParameters.toString());
        }
        return router;
    }

    public CommandResult goCompany(RequestContent content, 
            EnumSet<ParamType> parametersToValidate) {
        String page = ConfigurationManager.getProperty("path.page.addCompany");
        List<Locale> countryList = Arrays.asList(Locale.getISOCountries()).stream().
                map(e -> new Locale(EMPTY_STRING, e)).collect(Collectors.toList());
        content.setSessionAttribute(COUNTRY_LIST.getAttribute(), countryList);
        return new CommandResult(page, CommandResult.ResponseType.FORWARD);
    }

    public CommandResult searchCompany(RequestContent content, 
            EnumSet<ParamType> parametersToValidate) {
        return new CommandResult(ConfigurationManager.getProperty("path.page.profile"), 
                CommandResult.ResponseType.FORWARD);
    }

    public void createCompany(Company company) throws ServiceException {
        try (CompanyDaoImpl companyDao = new CompanyDaoImpl()) {
            companyDao.create(company);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Company> findAllCompanies() throws ServiceException {
        try (CompanyDaoImpl companyDao = new CompanyDaoImpl()) {
            return companyDao.findAll();
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public Company findCompanyById(int id) throws ServiceException {
        try (CompanyDaoImpl companyDao = new CompanyDaoImpl()) {
            return companyDao.findEntityById(id);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public Company findCompanyByTaxNumberAndCountry(String taxNumber, 
            String country) throws ServiceException {
        try (CompanyDaoImpl companyDao = new CompanyDaoImpl()) {
            return companyDao.findCompanyByTaxNumberAndCountry(taxNumber, country);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }
}
