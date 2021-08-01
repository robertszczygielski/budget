package com.forbusypeople.budget.repositories.entities;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.MonthsEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cyclical_expenses")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CyclicalExpensesEntity extends BaseBudgetEntity {

    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private ExpensesCategory category;
    @Enumerated(EnumType.STRING)
    private MonthsEnum month;
    private Integer day;

}
