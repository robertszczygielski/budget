package com.forbusypeople.budget.enums;

public enum FilterExpensesParametersEnum {
    FROM_DATE("from"),
    TO_DATE("to"),
    YEAR("year"),
    MONTH("month");

    private final String key;

    FilterExpensesParametersEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
