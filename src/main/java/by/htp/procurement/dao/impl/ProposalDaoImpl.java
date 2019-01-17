package by.htp.procurement.dao.impl;

import by.htp.procurement.dao.AbstractDao;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.entity.Company;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import by.htp.procurement.entity.Proposal;
import by.htp.procurement.pool.ConnectionPool;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProposalDaoImpl extends AbstractDao<Integer, Proposal> {

    private static final String SQL_INSERT_PROPOSAL = 
            "INSERT INTO Proposal (applied_at,application,filePath,tender_id,user_login) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE_PROPOSAL = 
            "UPDATE Proposal SET applied_at=?, application=?, filePath=?, tender_id=?, user_login=?  WHERE proposalId=?";
    private static final String SQL_DELETE_PROPOSAL = 
            "UPDATE Proposal SET isArchieved=1 WHERE proposalId=?";
    private static final String SQL_SELECT_ALL_PROPOSALS = "SELECT Proposal.id, Proposal.applied_at, Proposal.application, Proposal.filePath,"
            + " Proposal.isArchived, Tender.id AS tender_id, Tender.name AS tender_name, Tender.category, Tender.description, Tender.price,"
            + " Tender.deadline_at, Tender.published_at, Tender.isArchived AS tender_isArchived,Tender.winner, Company.id AS company_id, "
            + "Company.name AS company_name, Company.taxNumber, Company.web, Company.country, Company.isArchived AS company_isArchived, "
            + "User.login, User.firstName, User.lastName, User.email, User.isArchived AS user_isArchived FROM Proposal "
            + "JOIN Tender ON Proposal.tender_id=Tender.id "
            + "JOIN User ON Proposal.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id";
    private static final String SQL_SELECT_PROPOSAL_BY_ID = 
            "SELECT Proposal.id, Proposal.applied_at, Proposal.application, Proposal.filePath, Proposal.isArchived, Tender.id AS tender_id,"
            + " Tender.name AS tender_name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at, "
            + "Tender.isArchived AS tender_isArchived,Tender.winner, Company.id AS company_id, Company.name AS company_name, Company.taxNumber,"
            + " Company.web, Company.country, Company.isArchived AS company_isArchived, User.login, User.firstName, User.lastName, User.email,"
            + " User.isArchived AS user_isArchived FROM Proposal "
            + "JOIN Tender ON Proposal.tender_id=Tender.id "
            + "JOIN User ON Proposal.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id  WHERE Proposal.id=?";
    private static final String SQL_SELECT_PROPOSAL_BY_TENDER = 
            "SELECT Proposal.id, Proposal.applied_at, Proposal.application, Proposal.filePath, Proposal.isArchived, Tender.id AS tender_id, "
            + "Tender.name AS tender_name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at, "
            + "Tender.isArchived AS tender_isArchived,Tender.winner, Company.id AS company_id, Company.name AS company_name, Company.taxNumber,"
            + " Company.web, Company.country, Company.isArchived AS company_isArchived, User.login, User.firstName, User.lastName, User.email,"
            + " User.isArchived AS user_isArchived FROM Proposal "
            + "JOIN Tender ON Proposal.tender_id=Tender.id "
            + "JOIN User ON Proposal.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id WHERE tender_id=?";
    private static final String SQL_SELECT_PROPOSAL_BY_USER = 
            "SELECT Proposal.id, Proposal.applied_at, Proposal.application, Proposal.filePath, Proposal.isArchived, Tender.id AS tender_id,"
            + " Tender.name AS tender_name, Tender.category, Tender.description, Tender.price, Tender.deadline_at, Tender.published_at,"
            + " Tender.isArchived AS tender_isArchived,Tender.winner, Company.id AS company_id, Company.name AS company_name, Company.taxNumber,"
            + " Company.web, Company.country, Company.isArchived AS company_isArchived, User.login, User.firstName, User.lastName, User.email,"
            + " User.isArchived AS user_isArchived FROM Proposal "
            + "LEFT JOIN Tender ON Proposal.tender_id=Tender.id "
            + "JOIN User ON Proposal.user_login=User.login "
            + "JOIN Company ON User.company_id=Company.id WHERE Proposal.user_login=?";
    
    private static final String PARAM_ID = "id";
    private static final String PARAM_APPLIED_AT = "applied_at";
    private static final String PARAM_APPLICATION = "application";
    private static final String PARAM_FILE_PATH = "filePath";    
    private static final String PARAM_IS_ARCHIVED = "isArchived";   
    private static final String PARAM_COMPANY_ID = "company_id";
    private static final String PARAM_COMPANY_NAME = "company_name";
    private static final String PARAM_TAX_NUMBER = "taxNumber";
    private static final String PARAM_WEB = "web";
    private static final String PARAM_COUNTRY = "country";
    private static final String PARAM_FIRSTNAME = "firstName";
    private static final String PARAM_LASTNAME = "lastName";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_EMAIL = "email";    
    private static final String PARAM_TENDER_ID = "tender_id";
    private static final String PARAM_TENDER_NAME = "tender_name";
    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_PRICE = "price";
    private static final String PARAM_DEADLINE = "deadline_at";
    private static final String PARAM_PUBLISHED = "published_at";
    private static final String PARAM_TENDER_IS_ARCHIVED = "tender_isArchived";
    private static final String PARAM_USER_IS_ARCHIVED = "user_isArchived";
    private static final String PARAM_COMPANY_IS_ARCHIVED = "company_isArchived";

    private static Logger logger = LogManager.getLogger();

    public ProposalDaoImpl() {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    @Override
    public void create(Proposal proposal) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_INSERT_PROPOSAL)) {
            preparedStatement.setTimestamp(1, new Timestamp(proposal.getAppliedAt().getTime()));
            preparedStatement.setString(2, proposal.getApplication());
            preparedStatement.setString(3, proposal.getFilePath());
            preparedStatement.setInt(4, proposal.getTender().getId());
            preparedStatement.setString(5, proposal.getUser().getLogin());
            preparedStatement.executeUpdate();
            logger.info("Proposal created ");
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to create proposal", ex);
        }
    }

    @Override
    public void update(Proposal proposal) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_UPDATE_PROPOSAL)) {
            preparedStatement.setTimestamp(1, new Timestamp(proposal.getAppliedAt().getTime()));
            preparedStatement.setString(2, proposal.getApplication());
            preparedStatement.setString(3, proposal.getFilePath());
            preparedStatement.setInt(4, proposal.getTender().getId());
            preparedStatement.setString(5, proposal.getUser().getLogin());
            preparedStatement.setInt(6, proposal.getId());
            preparedStatement.executeUpdate();
            logger.info("Proposal updated " + proposal.getId());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to update proposal", ex);
        }
    }

    @Override
    public void delete(Proposal proposal) throws DaoException {
        Integer proposalId = proposal.getId();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_PROPOSAL)) {
            statement.setInt(1, proposalId);
            statement.executeUpdate();
            logger.info("Proposal deleted " + proposal.getId());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to delete proposal)", ex);
        }
    }

    @Override
    public List<Proposal> findAll() throws DaoException {
        List<Proposal> proposals = new ArrayList<>();
        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PROPOSALS)) {
            while (resultSet.next()) {
                Proposal proposal = new Proposal();
                proposal.setId(resultSet.getInt(PARAM_ID));
                proposal.setAppliedAt(resultSet.getTimestamp(PARAM_APPLIED_AT));
                proposal.setApplication(resultSet.getString(PARAM_APPLICATION));
                proposal.setFilePath(resultSet.getString(PARAM_FILE_PATH));
                proposal.setIsArchived(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Company company=new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                company.setIsArchieved(resultSet.getBoolean(PARAM_COMPANY_IS_ARCHIVED));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setIsArchieved(resultSet.getBoolean(PARAM_USER_IS_ARCHIVED));
                user.setCompany(company);
                proposal.setUser(user);
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                tender.setIsArchived(resultSet.getBoolean(PARAM_TENDER_IS_ARCHIVED));
                proposal.setTender(tender);
                proposals.add(proposal);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find all proposals", ex);
        }
        return proposals;
    }

    @Override
    public Proposal findEntityById(Integer proposalId) throws DaoException {
        Proposal proposal = new Proposal();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_PROPOSAL_BY_ID)) {
            preparedStatement.setInt(1, proposalId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                proposal.setId(resultSet.getInt(PARAM_ID));
                proposal.setAppliedAt(resultSet.getTimestamp(PARAM_APPLIED_AT));
                proposal.setApplication(resultSet.getString(PARAM_APPLICATION));
                proposal.setFilePath(resultSet.getString(PARAM_FILE_PATH));
                proposal.setIsArchived(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Company company=new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                company.setIsArchieved(resultSet.getBoolean(PARAM_COMPANY_IS_ARCHIVED));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setIsArchieved(resultSet.getBoolean(PARAM_USER_IS_ARCHIVED));
                user.setCompany(company);
                proposal.setUser(user);
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                tender.setIsArchived(resultSet.getBoolean(PARAM_TENDER_IS_ARCHIVED));
                proposal.setTender(tender);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find proposal by id", ex);
        }
        return proposal;
    }

    public Set<Proposal> findProposalsByTender(Integer tenderId) throws DaoException {
        Set<Proposal> proposals = new HashSet<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_PROPOSAL_BY_TENDER)) {
            preparedStatement.setInt(1, tenderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Proposal proposal = new Proposal();
                proposal.setId(resultSet.getInt(PARAM_ID));
                proposal.setAppliedAt(resultSet.getTimestamp(PARAM_APPLIED_AT));
                proposal.setApplication(resultSet.getString(PARAM_APPLICATION));
                proposal.setFilePath(resultSet.getString(PARAM_FILE_PATH));
                proposal.setIsArchived(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Company company=new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                company.setIsArchieved(resultSet.getBoolean(PARAM_COMPANY_IS_ARCHIVED));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setIsArchieved(resultSet.getBoolean(PARAM_USER_IS_ARCHIVED));
                user.setCompany(company);
                proposal.setUser(user);
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                tender.setIsArchived(resultSet.getBoolean(PARAM_TENDER_IS_ARCHIVED));
                proposal.setTender(tender);
                proposals.add(proposal);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find proposal by tender", ex);
        }
        return proposals;
    }

    public List<Proposal> findProposalsByUser(String login) throws DaoException {
        List<Proposal> proposals = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_PROPOSAL_BY_USER)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Proposal proposal = new Proposal();
                proposal.setId(resultSet.getInt(PARAM_ID));
                proposal.setAppliedAt(resultSet.getTimestamp(PARAM_APPLIED_AT));
                proposal.setApplication(resultSet.getString(PARAM_APPLICATION));
                proposal.setFilePath(resultSet.getString(PARAM_FILE_PATH));
                proposal.setIsArchived(resultSet.getBoolean(PARAM_IS_ARCHIVED));
                Company company=new Company();
                company.setId(resultSet.getInt(PARAM_COMPANY_ID));
                company.setName(resultSet.getString(PARAM_COMPANY_NAME));
                company.setTaxNumber(resultSet.getString(PARAM_TAX_NUMBER));
                company.setWeb(resultSet.getString(PARAM_WEB));
                company.setCountry(resultSet.getString(PARAM_COUNTRY));
                company.setIsArchieved(resultSet.getBoolean(PARAM_COMPANY_IS_ARCHIVED));
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_LOGIN));
                user.setFirstName(resultSet.getString(PARAM_FIRSTNAME));
                user.setLastName(resultSet.getString(PARAM_LASTNAME));
                user.setEmail(resultSet.getString(PARAM_EMAIL));
                user.setIsArchieved(resultSet.getBoolean(PARAM_USER_IS_ARCHIVED));
                user.setCompany(company);
                proposal.setUser(user);
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                tender.setName(resultSet.getString(PARAM_TENDER_NAME));
                tender.setCategory(resultSet.getString(PARAM_CATEGORY));
                tender.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                tender.setPrice(resultSet.getInt(PARAM_PRICE));
                tender.setDeadlineAt(resultSet.getTimestamp(PARAM_DEADLINE));
                tender.setPublishedAt(resultSet.getTimestamp(PARAM_PUBLISHED));
                tender.setIsArchived(resultSet.getBoolean(PARAM_TENDER_IS_ARCHIVED));
                proposal.setTender(tender);
                proposals.add(proposal);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find proposal by user ", ex);
        }
        return proposals;
    }
}
