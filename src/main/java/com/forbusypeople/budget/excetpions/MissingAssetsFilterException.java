package com.forbusypeople.budget.excetpions;

public class MissingAssetsFilterException extends RuntimeException {

    private final String errorCode;

    public MissingAssetsFilterException(String message,
                                        String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
