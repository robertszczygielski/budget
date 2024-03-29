package com.forbusypeople.budget.repositories.entities;

import com.forbusypeople.budget.enums.AssetCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "assets")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AssetEntity extends BaseBudgetEntity {

    private BigDecimal amount;
    private Instant incomeDate;
    @Enumerated(EnumType.STRING)
    private AssetCategory category;
    private String description;

}
