package edu.ucaldas.back.infra.exception;

public class EntityAlredyExists extends RuntimeException {
    public EntityAlredyExists() {
        super("Entity already exists.");
    }

    public EntityAlredyExists(String message) {
        super(message);
    }

    public EntityAlredyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlredyExists(Throwable cause) {
        super(cause);
    }

    public EntityAlredyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
