package by.htp.procurement.dao.impl;

import by.htp.procurement.dao.AbstractDao;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.entity.Company;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import by.htp.procurement.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TenderDaoImpl extends AbstractDao<Integer, Tender> {

    private static final String SQL_INSERT_TENDER = 
            "INSERT INTO Tender (name,category,description,price,deadline_at,user_login) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_TENDER = 
            "UPDATE Tender SET name=?, category=?, description=?, price=?, deadline_at=?, published_at=?, user_login=? WHERE id=?";
    private static final String SQL_UPDATE_WINNER_TENDER = 
            "UPDATE Tender SET winner=? WHERE id=?";
    private static final String SQL_INSERT_USER_TO_TENDER = 
            "INSERT INTO user_tender (tender_id,user_login) VALUES (?, ?)";
    private static final String SQL_DELETE_USER_FROM_TENDER = 
            "DELETE FROM user_tender WHERE tender_id=? AND user_login=?";
    private static final String SQL_DELETE_TENDER = 
            "UPDATE Tender SET isArchived=1 WHERE id=?";
    private static final String SQL_SELECT_ALL_TENDERS = 
            "SELECT Tender.id, Tender.name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at, "
            + "Company.id AS company_id, Company.name AS company_name, Company.taxNumber, Company.web, Company.country, User.login, "
            + "User.firstName, User.lastName, User.email FROM Tender "
            + "JOIN User ON Tender.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id";
    private static final String SQL_SELECT_PUBLISH_AND_NOT_ARCH = 
            "SELECT Tender.id, Tender.name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at, "
            + "Company.id AS company_id, Company.name AS company_name, Company.taxNumber, Company.web, Company.country, User.login, "
            + "User.firstName, User.lastName, User.email FROM Tender "
            + "JOIN User ON Tender.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id WHERE Tender.published_at IS NOT NULL AND Tender.isArchived=0";
    private static final String SQL_SELECT_TENDER_BY_ID = 
            "SELECT Tender.id, Tender.name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at, "
            + "Tender.isArchived,Tender.winner, Company.id AS company_id, Company.name AS company_name, Company.taxNumber, Company.web, "
            + "Company.country, User.login, User.firstName, User.lastName, User.email FROM Tender "
            + "JOIN User ON Tender.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id WHERE Tender.id=?";
    private static final String SQL_SELECT_TENDER_BY_USER = 
            "SELECT Tender.id, Tender.name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at, "
            + "Tender.isArchived, Company.id AS company_id, Company.name AS company_name, Company.taxNumber, Company.web, Company.country, "
            + "User.login, User.firstName, User.lastName, User.email FROM Tender "
            + "JOIN User ON Tender.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id WHERE Tender.user_login=?";
    private static final String SQL_SELECT_ACTIVE_TENDERS_BY_USER = 
            "SELECT Tender.id, Tender.name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at, "
            + "Tender.isArchived, Company.id AS company_id, Company.name AS company_name, Company.taxNumber, Company.web, Company.country, "
            + "User.login, User.firstName, User.lastName, User.email FROM Tender "
            + "JOIN User ON Tender.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id "
            + "WHERE Tender.user_login=? AND Tender.published_at IS NOT NULL AND Tender.isArchived=0 AND Tender.winner IS NULL";
    private static final String SQL_SELECT_USER_TENDER_COMMITTEE = 
            "SELECT user_tender.user_login as user_login FROM Tender JOIN user_tender on Tender.id=user_tender.tender_id WHERE Tender.id=?";
    private static final String SQL_SELECT_TENDER_BY_TC_MEMBER = 
            "SELECT Tender.id, Tender.name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at, "
            + "Tender.isArchived, Company.id AS company_id, Company.name AS company_name, Company.taxNumber, Company.web, Company.country, "
            + "User.login, User.firstName, User.lastName, User.email FROM Tender "
            + "JOIN User ON Tender.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id "
            + "JOIN user_tender on Tender.id=user_tender.tender_id WHERE user_tender.user_login=?";
    
    private static final String PARAM_COMPANY_ID = "company_id";
    private static final String PARAM_COMPANY_NAME = "company_name";
    private static final String PARAM_TAX_NUMBER = "taxNumber";
    private static final String PARAM_WEB = "web";
    private static final String PARAM_COUNTRY = "country";

    private static final String PARAM_FIRSTNAME = "firstName";
    private static final String PARAM_LASTNAME = "lastName";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_EMAIL = "email";

    private static final String PARAM_ID = "id";
    private static final String PARAM_TENDER_NAME = "name";
    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_PRICE = "price";
    private static final String PARAM_DEADLINE = "deadline_at";
    private static final String PARAM_PUBLISHED = "published_at";
    private static final String PARAM_IS_ARCHIVED = "isArchived";
    private static final String PARAM_WINNER = "winner";
    private static final String PARAM_USER_LOGIN = "user_login";

    private static Logger logger = LogManager.getLogger();

    public TenderDaoImpl() {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    @Override
    public void create(Tender tender) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_INSERT_TENDER)) {
            preparedStatement.setString(1, tender.getName());
            preparedStatement.setString(2, tender.getCategory());
            preparedStatement.setString(3, tender.getDescription());
            preparedStatement.setInt(4, tender.getPrice());
            preparedStatement.setTimestamp(5, new Timestamp(tender.getDeadlineAt().getTime()));
            preparedStatement.setString(6, tender.getUser().getLogin());
            preparedStatement.executeUpdate();
            logger.info("Tender created " + tender.getId());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to create tender", ex);
        }
    }

    @Override
    public void update(Tender tender) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_UPDATE_TENDER)) {
            preparedStatement.setString(1, tender.getName());
            preparedStatement.setString(2, tender.getCategory());
            preparedStatement.setString(3, tender.getDescription());
            preparedStatement.setInt(4, tender.getPrice());
            preparedStatement.setTimestamp(5, new Timestamp(tender.getDeadlineAt().getTime()));
            preparedStatement.setTimestamp(6, new Timestamp(tender.getPublishedAt().getTime()));
            preparedStatement.setString(7, tender.getUser().getLogin());
            preparedStatement.setInt(8, tender.getId());
            preparedStatement.executeUpdate();
            logger.info("Tender updated " + tender.getId());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to update  tender", ex);
        }
    }

    public void setWinner(Tender tender) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_UPDATE_WINNER_TENDER)) {
            preparedStatement.setInt(1, tender.getWinner().getId());
            preparedStatement.setInt(2, tender.getId());
            preparedStatement.executeUpdate();
            logger.info("Tender winner updated " + tender.getId());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to update  tender", ex);
        }
    }

    @Override
    public void delete(Tender tender) throws DaoException {
        Integer tenderId = tender.getId();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_TENDER)) {
            statement.setInt(1, tenderId);
            statement.executeUpdate();
            logger.info("Tender deleted " + tenderId);
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to delete tender)", ex);
        }
    }

    @Override
    public List<Tender> findAll() throws DaoException {
        List<Tender> tenders = new ArrayList<>();
        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TENDERS)) {
            while (resultSet.next()) {
                Tender tender = new Tender();
                tender.setId(resultSet.getInt(PARAM_ID));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setCompany(company);
                tender.setUser(user);
                tenders.add(tender);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find all tenders", ex);
        }
        return tenders;
    }

    public List<Tender> findTendersPublishedAndNotArchived() throws DaoException {
        List<Tender> tenders = new ArrayList<>();
        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_PUBLISH_AND_NOT_ARCH)) {
            while (resultSet.next()) {
                Tender tender = new Tender();
                tender.setId(resultSet.getInt(PARAM_ID));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setCompany(company);
                tender.setUser(user);
                tenders.add(tender);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find all tenders", ex);
        }
        return tenders;
    }

    @Override
    public Tender findEntityById(Integer tenderId) throws DaoException {
        Tender tender = new Tender();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_TENDER_BY_ID)) {
            preparedStatement.setInt(1, tenderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tender.setId(resultSet.getInt(PARAM_ID));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                tender.setIsArchived(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Integer winner_id = resultSet.getInt(PARAM_WINNER);
                if (winner_id > 0) {
                    Company winner = new Company();
                    winner.setId(winner_id);
                    tender.setWinner(winner);
                }
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setCompany(company);
                tender.setUser(user);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find tender by id", ex);
        }
        return tender;
    }

    public List<Tender> findTendersByUser(String login) throws DaoException {
        List<Tender> tenders = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_TENDER_BY_USER)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tender tender = new Tender();
                tender.setId(resultSet.getInt(PARAM_ID));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                tender.setIsArchived(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setCompany(company);
                tender.setUser(user);
                tenders.add(tender);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find tender by user", ex);
        }
        return tenders;
    }

    public List<Tender> findPublishedActiveTendersByUser(String login) throws DaoException {
        List<Tender> tenders = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_ACTIVE_TENDERS_BY_USER)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tender tender = new Tender();
                tender.setId(resultSet.getInt(PARAM_ID));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                tender.setIsArchived(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setCompany(company);
                tender.setUser(user);
                tenders.add(tender);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find tender by user", ex);
        }
        return tenders;
    }

    public void addUserToTenderCommittee(Tender entity, User user) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT_USER_TO_TENDER)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to add user to tender committee", ex);
        }
    }

    public void deleteUserFromTenderCommittee(Tender entity, User user) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_DELETE_USER_FROM_TENDER)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to delete user from tender committee", ex);
        }
    }

    public Set<User> findUsersInTenderCommittee(Integer tenderId) throws DaoException {
        Set<User> committee = new TreeSet<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_USER_TENDER_COMMITTEE)) {
            preparedStatement.setInt(1, tenderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User member = new User();
                member.setLogin(resultSet.getString(PARAM_USER_LOGIN));
                committee.add(member);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find tender committee members", ex);
        }
        return committee;
    }

    public List<Tender> findTendersAssignedToTCMember(String login) throws DaoException {
        List<Tender> tenders = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_TENDER_BY_TC_MEMBER)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tender tender = new Tender();
                tender.setId(resultSet.getInt(PARAM_ID));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                tender.setIsArchived(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Company company = new Company();
                company.setId(resultSet.getInt(PARAM_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setCompany(company);
                tender.setUser(user);
                tenders.add(tender);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find tenders assigned to committee member", ex);
        }
        return tenders;
    }
}
