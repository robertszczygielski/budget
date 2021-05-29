package com.forbusypeople.budget.controllers.handlers;

import com.forbusypeople.budget.controllers.handlers.dtos.ErrorMessage;
import com.forbusypeople.budget.excetpions.MissingExpensesFilterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExpensesControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
    public ErrorMessage missingExpensesFilterExceptionHandler(MissingExpensesFilterException exception) {
        return ErrorMessage.ErrorMessageBuilder.anErrorMessage()
                .withErrorCode(exception.getErrorCode())
                .withErrorDescription(exception.getMessage())
                .build();
    }

}
