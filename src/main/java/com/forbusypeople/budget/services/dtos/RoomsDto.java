package com.forbusypeople.budget.services.dtos;

import com.forbusypeople.budget.enums.RoomsType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class RoomsDto {

    private UUID id;
    private RoomsType type;
    private BigDecimal cost;

}
