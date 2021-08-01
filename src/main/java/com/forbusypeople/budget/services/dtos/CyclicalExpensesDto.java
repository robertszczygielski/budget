package com.forbusypeople.budget.services.dtos;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.MonthsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CyclicalExpensesDto {

    private UUID id;
    private BigDecimal amount;
    private ExpensesCategory category;
    private MonthsEnum month;
    Integer day;

}
