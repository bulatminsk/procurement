package by.htp.procurement.pool;

public class PoolException extends Exception {

    public PoolException() {
        super();
    }

    public PoolException(String cause) {
        super(cause);
    }

    public PoolException(Throwable t) {
        super(t);
    }

    public PoolException(String cause, Throwable t) {
        super(cause, t);
    }
}
