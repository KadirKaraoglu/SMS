package com.kadirkaraoglu.exception;

public class BaseException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.prepareErrorMessage());
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return errorMessage.getMessageType().getCode();
    }

    public String getMessage() {
        return errorMessage.getMessageType().getMessage();
    }
}