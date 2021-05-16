package com.forbusypeople.budget.validators;

import com.forbusypeople.budget.enums.FilterExceptionErrorMessages;
import com.forbusypeople.budget.excetpions.MissingExpensesFilterException;
import org.springframework.stereotype.Component;

@Component
public class ExpensesFilterParametersValidator extends FilterParametersValidator {

    @Override
    public void throwException(String missingKey,
                               String errorCode) {
        throw new MissingExpensesFilterException(
                FilterExceptionErrorMessages.MISSING_EXPENSES_FILTER_KEY.getMessage(missingKey),
                errorCode
        );
    }
}
