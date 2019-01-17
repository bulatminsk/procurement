package test.htp.procurement.pool;

import by.htp.procurement.pool.ConnectionPool;
import by.htp.procurement.pool.PoolException;
import by.htp.procurement.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class ConnectionPoolTest {
    private static Logger logger = LogManager.getLogger();

    @Test
    public void getConnectionPool() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Assert.assertNotNull(connectionPool);
    }

    @Test
    public void getConnection() {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        Assert.assertNotNull(connection);
        ConnectionPool.getInstance().returnConnection(connection);
    }

    @AfterClass
    public void tearDown() {
        try {
            ConnectionPool.getInstance().releasePool();
        } catch (PoolException e) {
            logger.error(e);
        }
    }
}
