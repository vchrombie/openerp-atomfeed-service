package org.bahmni.crater.web;

public class CraterException extends RuntimeException {
    public CraterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CraterException(Throwable cause) {
        super(cause);
    }

    public CraterException(String message) {
        super(message);
    }
}
