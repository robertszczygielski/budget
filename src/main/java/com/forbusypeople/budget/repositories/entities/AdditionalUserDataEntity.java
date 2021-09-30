package com.forbusypeople.budget.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "additional_user_data")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalUserDataEntity extends BaseBudgetEntity {

    private String email;

}
