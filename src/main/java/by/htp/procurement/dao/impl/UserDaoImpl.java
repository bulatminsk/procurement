package by.htp.procurement.dao.impl;

import by.htp.procurement.dao.AbstractDao;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.entity.Company;
import by.htp.procurement.entity.User;
import by.htp.procurement.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDaoImpl extends AbstractDao<String, User> {

    private static final String SQL_INSERT_USER = 
            "INSERT INTO User(login,firstName,lastName,email,password) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE_USER = 
            "UPDATE User SET firstName=?, lastName=?, email=?, company_id=? WHERE login=?";
    private static final String SQL_DELETE_USER = 
            "UPDATE User SET isArchieved=1 WHERE login=?";
    private static final String SQL_SELECT_ALL_USERS = 
            "SELECT User.login, User.firstName, User.lastName, User.email,Company.id as company_id, Company.name, "
            + "Company.taxNumber, Company.web FROM User "
            + "LEFT JOIN Company ON User.company_id=Company.id";
    private static final String SQL_SELECT_USER_BY_LOGIN = 
            "SELECT User.login, User.password, User.firstName, User.lastName, User.email, User.isArchived ,Company.id as company_id, "
            + "Company.name, Company.taxNumber, Company.web, Company.country, Company.isArchived as company_isArchived FROM User "
            + "LEFT JOIN Company ON User.company_id=Company.id WHERE login=?";
    private static final String SQL_SELECT_USER_BY_COMPANY = 
            "SELECT list_company.login FROM ("
            + "SELECT login FROM User WHERE company_id=?) list_company "
            + "WHERE login NOT IN ("
            + "SELECT list_tender.login AS login FROM  ("
            + "SELECT user_tender.user_login AS login FROM Tender "
            + "JOIN user_tender ON Tender.id=user_tender.tender_id WHERE tender.id=?)list_tender)";
    
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_FIRSTNAME = "firstName";
    private static final String PARAM_LASTNAME = "lastName";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_IS_ARCHIVED = "isArchived";
    private static final String PARAM_COMPANY_ID = "company_id";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_TAX_NUMBER = "taxNumber";
    private static final String PARAM_WEB = "web";
    private static final String PARAM_COUNTRY = "country";
    private static final String PARAM_COMPANY_IS_ARCHIVED = "company_isArchived";

    private static Logger logger = LogManager.getLogger();

    public UserDaoImpl() {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    @Override
    public void create(User user) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();
            logger.info("User created " + user.getLogin());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to create user ", ex);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getCompany().getId());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.executeUpdate();
            logger.info("User updated " + user.getLogin());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to update user", ex);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
        String login = user.getLogin();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_USER)) {
            statement.setString(1, login);
            statement.executeUpdate();
            logger.info("User deleted " + login);
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to delete user)", ex);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                company.setName(resultSet.getString(PARAM_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                user.setCompany(company);
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find all users ",ex);
        }
        return users;
    }

    @Override
    public User findEntityById(String login) throws DaoException {
        User user = null;
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setLogin(login);
                user.setPassword(resultSet.getString(PARAM_PASSWORD));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setIsArchieved(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                if (company.getId() != 0) {
                    company.setName(resultSet.getString(PARAM_NAME));
                    company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                    company.setWeb(resultSet.getString(PARAM_WEB));
                    company.setCountry(resultSet.getString(PARAM_COUNTRY));
                    company.setIsArchieved(resultSet.getBoolean(PARAM_COMPANY_IS_ARCHIVED));
                    user.setCompany(company);
                }
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find user by login ", ex);
        }
        return user;
    }

    public Set<User> findUsersNotInTenderCommitteeByCompanyId(Integer tenderId, Integer companyId) throws DaoException {
        Set<User> users = new TreeSet<>();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_USER_BY_COMPANY)) {
            statement.setInt(1, companyId);
            statement.setInt(2, tenderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find users which are not in tender by companyId", ex);
        }
        return users;
    }
}
