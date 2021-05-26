package com.forbusypeople.budget.enums;

public enum FilterSpecification {
    FOR_ASSETS("forAssetsValidator", "forAssetsRange"),
    FOR_EXPENSES("forExpensesValidator", "forExpensesRange");

    private final String forValidator;
    private final String forRange;

    FilterSpecification(String forValidator,
                        String forRange) {
        this.forValidator = forValidator;
        this.forRange = forRange;
    }

    public String getForValidator() {
        return forValidator;
    }

    public String getForRange() {
        return forRange;
    }
}
