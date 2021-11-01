package com.forbusypeople.budget.feign.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FeignNbpDto {

    private String currency;
    private String code;
    private List<RatesNbpDto> rates;

}
