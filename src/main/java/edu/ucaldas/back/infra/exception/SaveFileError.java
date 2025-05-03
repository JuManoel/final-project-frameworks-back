package edu.ucaldas.back.infra.exception;

public class SaveFileError extends RuntimeException {
    public SaveFileError() {
        super("Error saving file.");
    }

    public SaveFileError(String message) {
        super(message);
    }

    public SaveFileError(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveFileError(Throwable cause) {
        super(cause);
    }

    public SaveFileError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
