package com.forbusypeople.budget.services.dtos;

import com.forbusypeople.budget.enums.RoomsType;
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
public class RoomsDto {

    private UUID id;
    private RoomsType type;
    private BigDecimal cost;
    private Boolean rent;
    private String currency;

}
