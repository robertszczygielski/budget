package com.forbusypeople.budget.excetpions;

public class AssetIncompleteException extends BudgetMainException {

    public AssetIncompleteException(String message,
                                    String errorCode) {
        super(message, errorCode);
    }
}
