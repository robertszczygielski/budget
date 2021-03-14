package com.forbusypeople.budget.excetpions;

import com.forbusypeople.budget.enums.AuthenticationMessageEnum;

public class BudgetUserAlreadyExistsInDatabaseException extends RuntimeException {

    public BudgetUserAlreadyExistsInDatabaseException() {
        super(AuthenticationMessageEnum.USER_ALREADY_EXISTS.getMessage());
    }
}
