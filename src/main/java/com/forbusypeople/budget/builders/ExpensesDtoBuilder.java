package com.forbusypeople.budget.builders;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.services.dtos.ExpensesDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class ExpensesDtoBuilder {

    private UUID id;
    private BigDecimal amount;
    private Instant purchaseDate;
    private ExpensesCategory category;

    public ExpensesDtoBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public ExpensesDtoBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public ExpensesDtoBuilder withPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public ExpensesDtoBuilder withCategory(ExpensesCategory category) {
        this.category = category;
        return this;
    }

    public ExpensesDto build() {
        var dto = new ExpensesDto();
        dto.setId(this.id);
        dto.setAmount(this.amount);
        dto.setPurchaseDate(this.purchaseDate);
        dto.setCategory(this.category);

        return dto;
    }
}
