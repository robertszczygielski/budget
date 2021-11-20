package com.forbusypeople.budget.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Entity(name = "housing_maintenance_category")
public class HousingMaintenanceCategoryEntity extends BaseBudgetEntity {

    private String name;

}
