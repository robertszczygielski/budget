package com.forbusypeople.budget.excetpions;

public class MissingExpensesFilterException extends RuntimeException {

    private final String errorCode;

    public MissingExpensesFilterException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
