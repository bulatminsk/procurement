package by.htp.procurement.dao;

import by.htp.procurement.pool.ProxyConnection;
import java.sql.SQLException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractDao<K, T> implements AutoCloseable {

    private static Logger logger = LogManager.getLogger();

    protected ProxyConnection connection;

    public abstract void create(T entity) throws DaoException;

    public abstract void delete(T entity) throws DaoException;

    public abstract void update(T entity) throws DaoException;

    public abstract List<T> findAll() throws DaoException;

    public abstract T findEntityById(K id) throws DaoException;

    public void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error("SQLException during attempt to close operation", ex);
            }
        }
    }
}
