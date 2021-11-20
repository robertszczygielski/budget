package com.forbusypeople.budget.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HousingMaintenanceCategoryDto {

    private UUID id;
    private String name;

}
