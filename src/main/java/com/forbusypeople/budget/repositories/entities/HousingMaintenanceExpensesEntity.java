package com.forbusypeople.budget.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Entity(name = "housing_maintenance_expenses")
public class HousingMaintenanceExpensesEntity extends BaseBudgetEntity {

    private BigDecimal amount;
    @Column(name = "purchase_date")
    private Instant purchaseDate;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private HousingMaintenanceCategoryEntity category;

}
