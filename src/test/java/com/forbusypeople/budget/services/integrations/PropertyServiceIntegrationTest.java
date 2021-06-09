package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.services.dtos.PropertyDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PropertyServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveBasicPropertyInDatabase() {
        // given
        initDatabaseByPrimeUser();
        PropertyDto property = new PropertyDto();
        property.setPostCode("00-010");
        property.setCity("Warsaw");
        property.setStreet("Smerfetki");
        property.setHouse("12A");
        property.setSingle(false);
        property.setRooms(3);

        // when
        var result = propertyService.addProperty(property);

        // then
        assertThat(result).isNotNull();
    }
}
