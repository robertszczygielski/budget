package com.forbusypeople.budget.services.dtos;

import com.forbusypeople.budget.enums.AssetCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssetDto {

    private UUID id;
    private BigDecimal amount;
    private Instant incomeDate;
    private AssetCategory category;
    private String description;

}
