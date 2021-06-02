package com.forbusypeople.budget.enums;

public enum FilterParametersEnum {
    FROM_DATE("from"),
    TO_DATE("to"),
    YEAR("year"),
    MONTH("month"),
    CATEGORY("category");

    private final String key;

    FilterParametersEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
