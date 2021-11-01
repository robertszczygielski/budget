package com.forbusypeople.budget.feign.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RatesNbpDto {

    private BigDecimal mid;

}
