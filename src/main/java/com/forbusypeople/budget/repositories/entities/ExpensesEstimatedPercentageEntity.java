package com.forbusypeople.budget.repositories.entities;

import com.forbusypeople.budget.enums.ExpensesCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Entity(name = "expenses_estimate_percentage")
public class ExpensesEstimatedPercentageEntity extends BaseBudgetEntity {

    private BigDecimal percentage;
    @Enumerated(EnumType.STRING)
    private ExpensesCategory category;

}
