package com.forbusypeople.budget.services.integrations.properties;

import com.forbusypeople.budget.services.dtos.PropertyDto;

class InitPropertyData {

    public static String POST_CODE = "00-010";
    public static String CITY = "Warsaw";
    public static String STREET = "Smerfetki";
    public static String HOUSE = "12A";
    public static Boolean SINGLE = false;

    public static PropertyDto getPropertyDto() {
        return PropertyDto.builder()
                .postCode(POST_CODE)
                .city(CITY)
                .street(STREET)
                .house(HOUSE)
                .single(SINGLE)
                .build();

    }
}
