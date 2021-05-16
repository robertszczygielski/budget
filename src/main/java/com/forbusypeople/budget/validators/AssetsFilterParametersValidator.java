package com.forbusypeople.budget.validators;

import com.forbusypeople.budget.enums.FilterExceptionErrorMessages;
import com.forbusypeople.budget.excetpions.MissingAssetsFilterException;
import org.springframework.stereotype.Component;

@Component
public class AssetsFilterParametersValidator extends FilterParametersValidator {

    @Override
    public void throwException(String missingKey,
                               String errorCode) {
        throw new MissingAssetsFilterException(
                FilterExceptionErrorMessages.MISSING_ASSETS_FILTER_KEY.getMessage(missingKey),
                errorCode
        );
    }
}
