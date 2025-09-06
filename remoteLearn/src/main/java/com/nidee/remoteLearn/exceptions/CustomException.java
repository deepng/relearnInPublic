package com.nidee.remoteLearn.exceptions;

public final class CustomException extends Exception {

    private CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String errorCode, Throwable cause) {
        super(cause);
        logErrorMessage(errorCode, cause);
    }

    public void logErrorMessage(String errorCode, Throwable cause) {
        // Log the error with the error code
        System.out.println("Error Code: " + errorCode + ", Cause: " + cause.getMessage());
    }
}
