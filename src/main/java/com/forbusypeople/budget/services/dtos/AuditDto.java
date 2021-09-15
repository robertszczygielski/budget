package com.forbusypeople.budget.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDto {

    private BigDecimal percent;
    private BigDecimal currentAmount;
    private BigDecimal expectedAmount;

}
