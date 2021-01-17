package com.forbusypeople.budget.services.dtos;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AssetDto {

    private UUID id;
    private BigDecimal amount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetDto assetDto = (AssetDto) o;
        return Objects.equals(id, assetDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AssetDto{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
