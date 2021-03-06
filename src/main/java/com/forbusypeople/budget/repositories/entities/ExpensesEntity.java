package com.forbusypeople.budget.repositories.entities;

import com.forbusypeople.budget.enums.ExpensesCategory;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "expenses")
@Data
public class ExpensesEntity extends BaseBudgetEntity {

    private BigDecimal amount;
    private Instant purchaseDate;
    @Enumerated(EnumType.STRING)
    private ExpensesCategory category;

}
