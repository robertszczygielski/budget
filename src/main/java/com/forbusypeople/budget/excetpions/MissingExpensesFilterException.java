package com.forbusypeople.budget.excetpions;

public class MissingExpensesFilterException extends BudgetMainException {

    public MissingExpensesFilterException(String message,
                                          String errorCode) {
        super(message, errorCode);
    }

}
