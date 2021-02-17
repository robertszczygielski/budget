package com.forbusypeople.budget.validators;

import com.forbusypeople.budget.enums.ValidatorsAssetEnum;
import com.forbusypeople.budget.services.dtos.AssetDto;

import java.util.Objects;

class IncomeDateValidator implements Validator {

    @Override
    public ValidatorMessage valid(AssetDto dto, ValidatorMessage validatorMessage) {
        if (Objects.isNull(dto.getIncomeDate())) {
            validatorMessage.setMessage(ValidatorsAssetEnum.NO_INCOME_DATE.getMessage());
            validatorMessage.setCode("C6589CAF5CB648BF9990E78800AA5E03");
        }

        return validatorMessage;
    }
}
