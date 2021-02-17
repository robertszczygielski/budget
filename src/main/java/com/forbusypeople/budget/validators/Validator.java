package com.forbusypeople.budget.validators;

import com.forbusypeople.budget.services.dtos.AssetDto;

public interface Validator {

    ValidatorMessage valid(AssetDto dto, ValidatorMessage validatorMessage);

}
