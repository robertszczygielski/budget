package com.forbusypeople.budget.excetpions;

public class BudgetMainException extends RuntimeException {

    private final String errorCode;

    public BudgetMainException(String message,
                               String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
