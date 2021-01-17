package com.forbusypeople.budget.builders;

import com.forbusypeople.budget.repositories.entities.AssetEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class AssetEntityBuilder {

    private UUID id;
    private BigDecimal amount;

    public AssetEntityBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public AssetEntityBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public AssetEntity build() {
        var dto = new AssetEntity();
        dto.setAmount(this.amount);
        dto.setId(this.id);
        return dto;
    }

}
