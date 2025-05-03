package edu.ucaldas.back.infra.exception;

public class ErrorToken extends RuntimeException{
    
    public ErrorToken() {
        super();
    }

    public ErrorToken(String message) {
        super(message);
    }

    public ErrorToken(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorToken(Throwable cause) {
        super(cause);
    }

    public ErrorToken(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
