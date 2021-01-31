package com.forbusypeople.budget.validators;

import com.forbusypeople.budget.enums.ValidatorsAssetEnum;
import com.forbusypeople.budget.excetpions.AssertIncompleteException;
import com.forbusypeople.budget.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssertValidator {

    public void validate(AssetDto dto) {
        if (Objects.isNull(dto.getAmount())) {
            throw new AssertIncompleteException(ValidatorsAssetEnum.NO_AMOUNT.getMessage());
        }
    }

}
