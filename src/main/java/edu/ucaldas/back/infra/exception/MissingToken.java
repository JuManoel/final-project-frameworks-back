package edu.ucaldas.back.infra.exception;

public class MissingToken extends RuntimeException {
    public MissingToken() {
        super("Missing token in the request header.");
    }

    public MissingToken(String message) {
        super(message);
    }

    public MissingToken(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingToken(Throwable cause) {
        super(cause);
    }

    public MissingToken(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
