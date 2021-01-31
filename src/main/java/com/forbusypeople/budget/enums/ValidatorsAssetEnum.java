package com.forbusypeople.budget.enums;

public enum ValidatorsAssetEnum {

    NO_AMOUNT("no amount");

    private final String message;

    ValidatorsAssetEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
