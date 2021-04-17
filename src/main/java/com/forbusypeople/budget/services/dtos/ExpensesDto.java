package com.forbusypeople.budget.services.dtos;

import com.forbusypeople.budget.enums.ExpensesCategory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class ExpensesDto {

    private UUID id;
    private BigDecimal amount;
    private Instant purchaseData;
    private ExpensesCategory category;

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

    public Instant getPurchaseData() {
        return purchaseData;
    }

    public void setPurchaseData(Instant purchaseData) {
        this.purchaseData = purchaseData;
    }

    public ExpensesCategory getCategory() {
        return category;
    }

    public void setCategory(ExpensesCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpensesDto that = (ExpensesDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ExpensesDto{" +
                "id=" + id +
                ", amount=" + amount +
                ", purchaseData=" + purchaseData +
                ", category=" + category +
                '}';
    }
}
