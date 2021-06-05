package com.forbusypeople.budget.builders;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.services.dtos.AssetDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class AssetDtoBuilder {

    private UUID id;
    private BigDecimal amount;
    private Instant incomeDate;
    private AssetCategory category;
    private String description;

    public AssetDtoBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public AssetDtoBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public AssetDtoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public AssetDtoBuilder withIncomeDate(Instant incomeDate) {
        this.incomeDate = incomeDate;
        return this;
    }

    public AssetDtoBuilder withCategory(AssetCategory category) {
        this.category = category;
        return this;
    }

    public AssetDto build() {
        var dto = new AssetDto();
        dto.setAmount(this.amount);
        dto.setId(this.id);
        dto.setIncomeDate(this.incomeDate);
        dto.setCategory(this.category);
        dto.setDescription(this.description);
        return dto;
    }

}
