package com.forbusypeople.budget.enums;

public enum FilterSpecification {
    FOR_ASSETS("forAssetsValidator"),
    FOR_EXPENSES("forExpensesValidator");

    private final String forValidator;

    FilterSpecification(String forValidator) {
        this.forValidator = forValidator;
    }

    public String getForValidator() {
        return forValidator;
    }
}
