package edu.ucaldas.back.infra.exception;

public class DataNotFound extends RuntimeException {
    public DataNotFound() {
        super("Data not found.");
    }

    public DataNotFound(String message) {
        super(message);
    }

    public DataNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFound(Throwable cause) {
        super(cause);
    }

    public DataNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
