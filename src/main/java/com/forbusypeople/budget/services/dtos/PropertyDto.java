package com.forbusypeople.budget.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDto {

    private UUID id;
    private List<RoomsDto> rooms;
    private Boolean single;
    private String city;
    private String postCode;
    private String street;
    private String house;

}
