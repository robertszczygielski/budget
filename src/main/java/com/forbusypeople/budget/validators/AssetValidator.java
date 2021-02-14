package com.forbusypeople.budget.validators;

import com.forbusypeople.budget.enums.ValidatorsAssetEnum;
import com.forbusypeople.budget.excetpions.AssetIncompleteException;
import com.forbusypeople.budget.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetValidator {

    public void validate(AssetDto dto) {
        if (Objects.isNull(dto.getAmount())) {
            throw new AssetIncompleteException(ValidatorsAssetEnum.NO_AMOUNT.getMessage(), "EE48A612F34E402AAFA0871F66BB8F51");
        }
        if (Objects.isNull(dto.getIncomeDate())) {
            throw new AssetIncompleteException(ValidatorsAssetEnum.NO_INCOME_DATE.getMessage(), "C6589CAF5CB648BF9990E78800AA5E03");
        }
    }

}
