package by.htp.procurement.pool;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.htp.procurement.pool.DataBaseManager.*;

public class ConnectionPool implements Cloneable{

    private static Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private static ReentrantLock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> availableConnections;

    private ConnectionPool() {
        if (instance != null) {
            throw new RuntimeException("Reflection defender");
        }
        createPool();
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private void createPool() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException ex) {
            logger.fatal("Driver not found ", ex);
            throw new RuntimeException(ex);
        }
        this.availableConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection;
            try {
                connection = new ProxyConnection(DataBaseManager.getConnection());
                availableConnections.put(connection);
            } catch (InterruptedException | SQLException ex) {
                logger.error("Impossible to create connection ", ex);
                Thread.currentThread().interrupt();
                if (availableConnections.isEmpty()) {
                    logger.fatal("Impossible to create connections ", ex);
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
        } catch (InterruptedException ex) {
            logger.error("InterruptedException during attempt to get connection ", ex);
            Thread.currentThread().interrupt();            
        }
        return connection;
    }

    public void returnConnection(ProxyConnection connection) {
        boolean isAutoCommit;
        try {
            isAutoCommit = connection.getAutoCommit();
            if (!isAutoCommit) {
                connection.setAutoCommit(true);
            }
            availableConnections.put(connection);
        } catch (InterruptedException ex) {
            logger.error("Return connection failed ", ex);
            Thread.currentThread().interrupt();
        } catch (SQLException ex) {
            logger.error("Return connection failed ", ex);
        }
    }

    public void releasePool() throws PoolException {
        ProxyConnection connection;
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                connection = availableConnections.take();
                connection.closeConnection();
            }
        } catch (InterruptedException ex) {
            logger.error("Release pool failed ", ex);
            Thread.currentThread().interrupt();
        } catch (SQLException ex) {
            logger.error("Release pool failed ", ex);
            throw new PoolException(ex);
        } finally {
            deregisterDrivers();
        }
    }

    private void deregisterDrivers() throws PoolException {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException ex) {
            logger.error("Driver deregister failed ", ex);
            throw new PoolException(ex);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();        
    }
}
