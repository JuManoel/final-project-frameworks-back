package edu.ucaldas.back.infra.exception;

public class NotPermited extends RuntimeException {
    public NotPermited() {
        super("You are not allowed to perform this action.");
    }

    public NotPermited(String message) {
        super(message);
    }

    public NotPermited(String message, Throwable cause) {
        super(message, cause);
    }

    public NotPermited(Throwable cause) {
        super(cause);
    }

    public NotPermited(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
