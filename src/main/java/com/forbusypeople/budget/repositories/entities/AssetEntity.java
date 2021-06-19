package com.forbusypeople.budget.repositories.entities;

import com.forbusypeople.budget.enums.AssetCategory;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "assets")
@Data
public class AssetEntity extends BaseBudgetEntity {

    private BigDecimal amount;
    private Instant incomeDate;
    @Enumerated(EnumType.STRING)
    private AssetCategory category;
    private String description;

}
