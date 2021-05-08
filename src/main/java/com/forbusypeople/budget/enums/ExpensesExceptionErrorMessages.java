package com.forbusypeople.budget.enums;

public enum ExpensesExceptionErrorMessages {
    MISSING_FILTER_KEY("Missing filter key: ");

    private final String message;

    ExpensesExceptionErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage(String missingKey) {
        return this.message + missingKey;
    }
}
