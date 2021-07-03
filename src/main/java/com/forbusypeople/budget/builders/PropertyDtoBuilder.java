package com.forbusypeople.budget.builders;

import com.forbusypeople.budget.services.dtos.PropertyDto;

import java.util.UUID;


// TODO: do usunięcia na następnej lekcji
public class PropertyDtoBuilder {

    private UUID id;
    private Integer rooms;
    private Boolean single;
    private String city;
    private String postCode;
    private String street;
    private String house;

    public PropertyDto build() {
        PropertyDto dto = new PropertyDto();
        dto.setId(this.id);
        dto.setSingle(this.single);
        dto.setCity(this.city);
        dto.setPostCode(this.postCode);
        dto.setStreet(this.street);
        dto.setHouse(this.house);

        return dto;
    }

    public PropertyDtoBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public PropertyDtoBuilder withPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public PropertyDtoBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public PropertyDtoBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public PropertyDtoBuilder withHouse(String house) {
        this.house = house;
        return this;
    }

    public PropertyDtoBuilder withSingle(boolean single) {
        this.single = single;
        return this;
    }

    public PropertyDtoBuilder withRooms(int rooms) {
        this.rooms = rooms;
        return this;
    }
}
