package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.builders.PropertyDtoBuilder;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class PropertyServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveBasicPropertyInDatabase() {
        // given
        var postCode = "00-010";
        var city = "Warsaw";
        var street = "Smerfetki";
        var house = "12A";
        var single = false;
        var rooms = 3;
        initDatabaseByPrimeUser();
        PropertyDto property = new PropertyDtoBuilder()
                .withPostCode(postCode)
                .withCity(city)
                .withStreet(street)
                .withHouse(house)
                .withSingle(single)
                .withRooms(rooms)
                .build();

        // when
        var result = propertyService.addProperty(property);

        // then
        assertThat(result).isNotNull();
        var entityList = propertyRepository.findAll();
        assertThat(entityList).hasSize(1);
        var entity = entityList.get(0);
        assertAll(
                () -> assertThat(entity.getRooms()).isEqualTo(rooms),
                () -> assertThat(entity.getPostCode()).isEqualTo(postCode),
                () -> assertThat(entity.getCity()).isEqualTo(city),
                () -> assertThat(entity.getStreet()).isEqualTo(street),
                () -> assertThat(entity.getHouse()).isEqualTo(house)
        );

    }

    @Test
    void shouldFindOnePropertyInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByProperty(user);


        // when
        var dtoList = propertyService.findAllProperties();

        // then
        assertThat(dtoList).hasSize(1);

    }
}
