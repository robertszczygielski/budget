package com.forbusypeople.budget.excetpions;

import com.forbusypeople.budget.enums.AuthenticationMessageEnum;

public class BudgetInvalidUsernameOrPasswordException extends RuntimeException {

    public BudgetInvalidUsernameOrPasswordException() {
        super(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }
}
