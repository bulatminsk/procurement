package by.htp.procurement.tag;

import by.htp.procurement.entity.Company;
import by.htp.procurement.logic.CommonService;
import by.htp.procurement.logic.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static javax.servlet.jsp.tagext.Tag.*;


public class CompanySearchTag extends TagSupport {

    private static final String ATTRIBUTE_SEARCH = "search";
    private static final String ATTRIBUTE_FIELD = "field";
    private static final String ATTRIBUTE_COMPANY_LIST = "companyList";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_TAX_NUMBER = "taxNumber";
    private static final String PARAM_COUNTRY = "country";
    private String field = null;
    private String search = null;
    private List<Company> companyList = null;

    private static Logger logger = LogManager.getLogger();

    public CompanySearchTag() {
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public int doStartTag() {
        companyList = new ArrayList<>();
        search = (String) pageContext.getRequest().getParameter(ATTRIBUTE_SEARCH);
        field = (String) pageContext.getRequest().getParameter(ATTRIBUTE_FIELD);
        if (search != null) {
            try {
                for (Company company : new CommonService().findAllCompanies()) {
                    boolean found = false;
                    switch (field) {
                        case PARAM_TAX_NUMBER:
                            found = company.getTaxNumber().toLowerCase().contains(search);
                            break;
                        case PARAM_NAME:
                            found = company.getName().toLowerCase().contains(search);
                            break;
                        case PARAM_COUNTRY:
                            found = company.getCountry().toLowerCase().contains(search);
                            break;
                    }
                    if (found) {
                        companyList.add(company);
                    }
                }
                pageContext.getRequest().setAttribute(ATTRIBUTE_COMPANY_LIST, companyList);
            } catch (ServiceException ex) {
                logger.error("ServiceException at attempt to findAll companies in CompanySearchTag ",ex);
            }
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
