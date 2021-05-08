package com.forbusypeople.budget.validators;

import com.forbusypeople.budget.enums.ExpensesExceptionErrorMessages;
import com.forbusypeople.budget.excetpions.MissingExpensesFilterException;
import org.springframework.stereotype.Component;

@Component
public class ExpensesFilerParametersValidator extends FilterParametersValidator {

    @Override
    public void throwException(String missingKey, String errorCode) {
        throw new MissingExpensesFilterException(
                ExpensesExceptionErrorMessages.MISSING_FILTER_KEY.getMessage(missingKey),
                errorCode);
    }
}
