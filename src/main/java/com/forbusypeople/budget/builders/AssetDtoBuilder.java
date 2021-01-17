package com.forbusypeople.budget.builders;

import com.forbusypeople.budget.services.dtos.AssetDto;

import java.math.BigDecimal;
import java.util.UUID;

public class AssetDtoBuilder {

    private UUID id;
    private BigDecimal amount;

    public AssetDtoBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public AssetDtoBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public AssetDto build() {
        var dto = new AssetDto();
        dto.setAmount(this.amount);
        dto.setId(this.id);
        return dto;
    }

}
