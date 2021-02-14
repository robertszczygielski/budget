package com.forbusypeople.budget.enums;

public enum ValidatorsAssetEnum {

    NO_AMOUNT("no amount"),
    NO_INCOME_DATE("no income date");

    private final String message;

    ValidatorsAssetEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
