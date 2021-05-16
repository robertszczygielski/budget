package com.forbusypeople.budget.enums;

public enum FilterExceptionErrorMessages {
    MISSING_EXPENSES_FILTER_KEY("Missing filter key for Expenses: "),
    MISSING_ASSETS_FILTER_KEY("Missing filter key for Assets: ");

    private final String message;

    FilterExceptionErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage(String missingKey) {
        return this.message + missingKey;
    }
}
