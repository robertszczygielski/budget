package com.forbusypeople.budget.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HousingMaintenanceExpensesDto {

    private UUID id;
    private String description;
    private BigDecimal amount;
    private Instant purchaseDate;
    private HousingMaintenanceCategoryDto category;
    
}
