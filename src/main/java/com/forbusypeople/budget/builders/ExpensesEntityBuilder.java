package com.forbusypeople.budget.builders;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class ExpensesEntityBuilder {

    private UUID id;
    private BigDecimal amount;
    private Instant purchaseDate;
    private UserEntity user;
    private ExpensesCategory category;

    public ExpensesEntityBuilder withUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public ExpensesEntityBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public ExpensesEntityBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public ExpensesEntityBuilder withPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public ExpensesEntityBuilder withCategory(ExpensesCategory category) {
        this.category = category;
        return this;
    }

    public ExpensesEntity build() {
        var entity = new ExpensesEntity();
        entity.setUser(this.user);
        entity.setId(this.id);
        entity.setCategory(this.category);
        entity.setPurchaseDate(this.purchaseDate);
        entity.setAmount(this.amount);
        
        return entity;
    }
}
