package by.htp.procurement.dao.impl;

import by.htp.procurement.dao.AbstractDao;
import by.htp.procurement.dao.DaoException;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.Evaluation;
import by.htp.procurement.entity.User;
import by.htp.procurement.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EvaluationDaoImpl extends AbstractDao<Integer, Evaluation> {

    private static final String SQL_INSERT_EVALUATION = 
            "INSERT INTO Evaluation (criteria,weight,tender_id) "
            + "SELECT ?,?,Tender.id FROM Tender WHERE Tender.id=? AND isArchived=0 AND published_at IS NULL";
    private static final String SQL_UPDATE_EVALUATION_SCORE_BY_USER = 
            "UPDATE  proposal_user_evaluation SET score =? WHERE user_login=?  AND proposal_id=? AND evaluation_id=?";
    private static final String SQL_DELETE_EVALUATION = 
            "DELETE FROM Evaluation WHERE id=? and tender_id=("
            + "SELECT Tender.id FROM Tender  WHERE isArchived=0 AND published_at IS NULL and Tender.id=?)";
    private static final String SQL_UPDATE_EVALUATION = 
            "UPDATE Evaluation SET criteria=?, weight=?, tender_id=? WHERE id=?";
    private static final String SQL_SELECT_ALL_EVALUATIONS = 
            "SELECT id, criteria, weight, tender_id, max_score FROM Evaluation";
    private static final String SQL_SELECT_EVALUATION_BY_ID = 
            "SELECT id, criteria, weight, tender_id, max_score FROM Evaluation WHERE id=?";
    private static final String SQL_SELECT_EVALUATION_BY_TENDER = 
            "SELECT id, criteria, weight, tender_id, max_score FROM Evaluation WHERE tender_id=?";
    private static final String SQL_SELECT_EVALUATION_BY_USER = 
            "SELECT Evaluation.id, Evaluation.criteria, Evaluation.weight, Evaluation.max_score, "
            + "Evaluation.tender_id, PUE.score AS score FROM  Evaluation "
            + "JOIN  proposal_user_evaluation AS PUE ON  (PUE.evaluation_id=Evaluation.id) "
            + "WHERE PUE.user_login=? AND Evaluation.tender_id=? AND PUE.proposal_id=?";
    private static final String SQL_SELECT_PUES = 
            "SELECT User.login AS user_login, Evaluation.id, Evaluation.criteria, Evaluation.weight, "
            + "Evaluation.max_score, Evaluation.tender_id, PUE.proposal_id, PUE.score FROM User "
            + "LEFT JOIN proposal_user_evaluation AS PUE ON (User.login=PUE.user_login) "
            + "JOIN Evaluation ON (PUE.evaluation_id=Evaluation.id) WHERE proposal_id=?";
    private static final String SQL_SELECT_PROPOSAL_FINAL_SCORE = 
            "SELECT SUM(PUE.score*evaluation.weight) AS final_score  FROM proposal_user_evaluation as PUE "
            + "JOIN evaluation on PUE.evaluation_id=evaluation.id WHERE PUE.proposal_id=?";
    
    private static final String PARAM_ID = "id";
    private static final String PARAM_EVALUATION_CRITERIA = "criteria";
    private static final String PARAM_EVALUATION_WEIGHT = "weight";
    private static final String PARAM_EVALUATION_MAX_SCORE = "max_score";
    private static final String PARAM_TENDER_ID = "tender_id";
    private static final String PARAM_USER_LOGIN = "user_login";
    private static final String PARAM_FINAL_SCORE = "final_score";
    private static final String PARAM_SCORE = "score";

    private static Logger logger = LogManager.getLogger();

    public EvaluationDaoImpl() {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    @Override
    public void create(Evaluation evaluation) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_INSERT_EVALUATION)) {
            preparedStatement.setString(1, evaluation.getCriteria());
            preparedStatement.setDouble(2, evaluation.getWeight());
            preparedStatement.setInt(3, evaluation.getTender().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to evaluation ", ex);
        }
    }

    @Override
    public void update(Evaluation evaluation) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_UPDATE_EVALUATION)) {
            preparedStatement.setString(1, evaluation.getCriteria());
            preparedStatement.setDouble(2, evaluation.getWeight());
            preparedStatement.setInt(3, evaluation.getTender().getId());
            preparedStatement.executeUpdate();
            logger.info("Evaluation updated " + evaluation.getId());
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find update evaluation", ex);
        }
    }

    @Override
    public void delete(Evaluation evaluation) throws DaoException {
        Integer evaluationId = evaluation.getId();
        Integer tenderId = evaluation.getTender().getId();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_EVALUATION)) {
            statement.setInt(1, evaluationId);
            statement.setInt(2, tenderId);
            statement.executeUpdate();
            logger.info("Evaluation deleted " + evaluationId);
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to delete evaluation)", ex);
        }
    }

    @Override
    public List<Evaluation> findAll() throws DaoException {
        List<Evaluation> evaluations = new ArrayList<>();
        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_EVALUATIONS)) {
            while (resultSet.next()) {
                Evaluation evaluation = new Evaluation();
                evaluation.setId(resultSet.getInt(PARAM_ID));
                evaluation.setCriteria(resultSet.getString(PARAM_EVALUATION_CRITERIA));
                evaluation.setWeight(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_WEIGHT)));
                evaluation.setMaxScore(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_MAX_SCORE)));
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                evaluation.setTender(tender);
                evaluations.add(evaluation);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find all evaluations", ex);
        }
        return evaluations;
    }

    @Override
    public Evaluation findEntityById(Integer evaluationId) throws DaoException {
        Evaluation evaluation = new Evaluation();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_EVALUATION_BY_ID)) {
            preparedStatement.setInt(1, evaluationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                evaluation.setId(resultSet.getInt(PARAM_ID));
                evaluation.setCriteria(resultSet.getString(PARAM_EVALUATION_CRITERIA));
                evaluation.setWeight(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_WEIGHT)));
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                evaluation.setTender(tender);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find evaluation by id", ex);
        }
        return evaluation;
    }

    public void updateProposalEvaluationByUser(Integer proposalId, String login, Map<Integer, Integer> scoresToUpdate) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_UPDATE_EVALUATION_SCORE_BY_USER)) {
            for (Map.Entry<Integer, Integer> entry : scoresToUpdate.entrySet()) {
                preparedStatement.setInt(1, entry.getValue());
                preparedStatement.setString(2, login);
                preparedStatement.setInt(3, proposalId);
                preparedStatement.setInt(4, entry.getKey());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to updateProposalEvaluationByUser", ex);
        }

    }

    public Set<Evaluation> findEvaluationsByTender(Integer tender_id) throws DaoException {
        Set<Evaluation> evaluations = new TreeSet<>();
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_SELECT_EVALUATION_BY_TENDER)) {
            preparedStatement.setInt(1, tender_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Evaluation evaluation = new Evaluation();
                evaluation.setId(resultSet.getInt(PARAM_ID));
                evaluation.setCriteria(resultSet.getString(PARAM_EVALUATION_CRITERIA));
                evaluation.setWeight(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_WEIGHT)));
                evaluation.setMaxScore(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_MAX_SCORE)));
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                evaluation.setTender(tender);
                evaluations.add(evaluation);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to findEvaluationsByTender ", ex);
        }
        return evaluations;
    }

    public Map<Evaluation, Integer> findEvaluationsAssignedToUser(Map<Evaluation, Integer> evaluations, String login, Integer tenderId, Integer proposalId) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_SELECT_EVALUATION_BY_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, tenderId);
            preparedStatement.setInt(3, proposalId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Evaluation evaluation = new Evaluation();
                evaluation.setId(Integer.parseInt(resultSet.getString(PARAM_ID)));
                evaluation.setCriteria(resultSet.getString(PARAM_EVALUATION_CRITERIA));
                evaluation.setWeight(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_WEIGHT)));
                evaluation.setMaxScore(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_MAX_SCORE)));
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                evaluation.setTender(tender);
                Integer score = Integer.parseInt(resultSet.getString(PARAM_SCORE));
                evaluations.put(evaluation, score);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to findEvaluationsAssignedToUser ", ex);
        }
        return evaluations;
    }

    public Integer countFinalScoreForProposal(Integer proposalId) throws DaoException {
        Integer final_score = 0;
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_SELECT_PROPOSAL_FINAL_SCORE)) {
            preparedStatement.setInt(1, proposalId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final_score = rs.getInt(PARAM_FINAL_SCORE);
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to countFinalScoreForProposal ", ex);
        }
        return final_score;
    }

    public Map<User, Map<Evaluation, Integer>> findProposalUserEvaluations(Map<User, Map<Evaluation, Integer>> pues, Integer proposalId) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection
                .prepareStatement(SQL_SELECT_PUES)) {
            preparedStatement.setInt(1, proposalId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(PARAM_USER_LOGIN));
                Evaluation evaluation = new Evaluation();
                evaluation.setId(Integer.parseInt(resultSet.getString(PARAM_ID)));
                evaluation.setCriteria(resultSet.getString(PARAM_EVALUATION_CRITERIA));
                evaluation.setWeight(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_WEIGHT)));
                evaluation.setMaxScore(Integer.parseInt(resultSet.getString(PARAM_EVALUATION_MAX_SCORE)));
                Tender tender = new Tender();
                tender.setId(Integer.parseInt(resultSet.getString(PARAM_TENDER_ID)));
                evaluation.setTender(tender);
                Integer score = Integer.parseInt(resultSet.getString(PARAM_SCORE));
                if (pues.get(user) == null) {
                    Map<Evaluation, Integer> evaluations = new TreeMap<>();
                    evaluations.put(evaluation, score);
                    pues.put(user, evaluations);
                } else {
                    pues.get(user).put(evaluation, score);
                }
            }
        } catch (SQLException ex) {
            throw new DaoException("SQLException during attempt to find user evaluations ", ex);
        }
        return pues;
    }
}
