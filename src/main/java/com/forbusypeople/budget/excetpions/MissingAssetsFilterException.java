package com.forbusypeople.budget.excetpions;

public class MissingAssetsFilterException extends BudgetMainException {

    public MissingAssetsFilterException(String message,
                                        String errorCode) {
        super(message, errorCode);
    }

}
