package by.htp.procurement.dao.impl;

import by.htp.procurement.dao.AbstractDao;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.entity.Company;
import by.htp.procurement.pool.ConnectionPool;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanyDaoImpl extends AbstractDao<Integer, Company> {

    private static final String SQL_INSERT_COMPANY = "INSERT INTO Company (name,taxNumber,web,country) VALUES (?,?,?,?)";
    private static final String SQL_DELETE_COMPANY = "DELETE FROM Company WHERE companyId=?";
    private static final String SQL_UPDATE_COMPANY = "UPDATE Company SET name=?, taxNumber=?,web=?,country=?  WHERE id=?";
    private static final String SQL_SELECT_ALL_COMPANIES = "SELECT Company.id, Company.name, Company.taxNumber, Company.web, Company.country FROM Company";
    private static final String SQL_SELECT_COMPANY_BY_ID = "SELECT Company.id, Company.name, Company.taxNumber, Company.web, Company.country FROM Company WHERE id=?";
    private static final String SQL_SELECT_BY_TAX_AND_COUNTRY = "SELECT Company.id, Company.name, Company.taxNumber, Company.web, Company.country FROM Company WHERE taxNumber=? AND country=?";
    private static final String PARAM_ID = "id";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_TAX_NUMBER = "taxNumber";
    private static final String PARAM_WEB = "web";
    private static final String PARAM_COUNTRY = "country";

    private static Logger logger = LogManager.getLogger();

    public CompanyDaoImpl() {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    @Override
    public void create(Company company) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_INSERT_COMPANY)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getTaxNumber());
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getCountry());
            preparedStatement.executeUpdate();
            logger.info("Company created ");
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to create company ", ex);
        }
    }

    @Override
    public void update(Company company) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_UPDATE_COMPANY)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getTaxNumber());
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getCountry());
            preparedStatement.setInt(5, company.getId());
            preparedStatement.executeUpdate();
            logger.info("Company updated " + company.getId());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to update company", ex);
        }
    }

    @Override
    public void delete(Company company) throws DaoException {
        Integer id = company.getId();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_COMPANY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.info("Company deleted " + id);
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to delete company)", ex);
        }
    }

    @Override
    public List<Company> findAll() throws DaoException {
        List<Company> companies = new ArrayList<Company>();
        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COMPANIES)) {
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_ID));
                company.setName(resultSet.getString(PARAM_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                companies.add(company);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find All companies)", ex);
        }
        return companies;
    }

    @Override
    public Company findEntityById(Integer id) throws DaoException {
        Company company = new Company();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_COMPANY_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company.setId(resultSet.getInt(PARAM_ID));
                company.setName(resultSet.getString(PARAM_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
            }
        } catch (SQLException ex) {
            throw new DaoException("Exception during attempt to find company by id", ex);
        }
        return company;
    }

    public Company findCompanyByTaxNumberAndCountry(String taxNumber, String country) throws DaoException {
        Company company = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_BY_TAX_AND_COUNTRY)) {
            preparedStatement.setString(1, taxNumber);
            preparedStatement.setString(2, country);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company = new Company();
                company.setId(resultSet.getInt(PARAM_ID));
                company.setName(resultSet.getString(PARAM_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
            }
        } catch (SQLException ex) {
            throw new DaoException("Exception during attempt to find company by taxNumber and country", ex);
        }
        return company;
    }
}
