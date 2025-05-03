package edu.ucaldas.back.infra.exception;

public class MissingData extends RuntimeException {
    public MissingData() {
        super("Missing data in the request body.");
    }

    public MissingData(String message) {
        super(message);
    }

    public MissingData(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingData(Throwable cause) {
        super(cause);
    }

    public MissingData(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
