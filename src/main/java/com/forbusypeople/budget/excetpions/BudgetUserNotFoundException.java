package com.forbusypeople.budget.excetpions;

import com.forbusypeople.budget.enums.AuthenticationMessageEnum;

public class BudgetUserNotFoundException extends RuntimeException {

    public BudgetUserNotFoundException() {
        super(AuthenticationMessageEnum.USER_NOT_FOUND.getMessage());
    }
}
