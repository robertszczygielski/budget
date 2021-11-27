package com.forbusypeople.budget.services.integrations.properties;

import com.forbusypeople.budget.enums.RoomsType;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import com.forbusypeople.budget.services.dtos.RoomsDto;
import com.forbusypeople.budget.services.integrations.InitIntegrationTestData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static com.forbusypeople.budget.services.integrations.properties.InitPropertyData.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class PropertyServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveBasicPropertyInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        PropertyDto property = getPropertyDto();

        // when
        var result = propertyService.saveProperty(user, property);

        // then
        assertThat(result).isNotNull();
        var entityList = propertyRepository.findAll();
        assertThat(entityList).hasSize(1);
        var entity = entityList.get(0);
        assertAll(
                () -> assertThat(entity.getPostCode()).isEqualTo(POST_CODE),
                () -> assertThat(entity.getCity()).isEqualTo(CITY),
                () -> assertThat(entity.getStreet()).isEqualTo(STREET),
                () -> assertThat(entity.getHouse()).isEqualTo(HOUSE)
        );

    }

    @Test
    void shouldFindOnePropertyInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByProperty(user);

        // when
        var dtoList = propertyService.findAllProperties(false);

        // then
        assertThat(dtoList).hasSize(1);

    }

    @Test
    void shouldUpdatePropertyInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByProperty(user);
        var roomType = RoomsType.ROOM_XL;
        var roomId = initDatabaseByRoom(roomType, BigDecimal.TEN, user);

        PropertyDto property = propertyService.findAllProperties(false).stream().findFirst().get();
        assertThat(property.getRooms()).isNull();

        var single = property.getSingle();

        var newSingle = !single;
        var newRooms = roomsService.getAll().stream()
                .filter(it -> it.getId().equals(roomId))
                .collect(Collectors.toList());

        property.setSingle(newSingle);
        property.setRooms(newRooms);

        // when
        propertyService.updateProperty(property);

        // then
        var entity = propertyRepository.findAll().stream().findFirst().get();
        assertThat(entity.getSingle()).isEqualTo(newSingle);
        assertThat(entity.getRooms()).hasSize(1);
        assertThat(entity.getRooms().get(0).getType()).isEqualTo(roomType);

    }

    @Test
    void shouldUpdatePropertyInDataBaseAsSold() {
        // given
        var user = initDatabaseByPrimeUser();
        var roomXlId = initDatabaseByRoom(RoomsType.ROOM_XL, BigDecimal.TEN, user);
        initDatabaseByProperty(user, roomXlId);

        var propertyNotSold = propertyRepository.findAll().stream().findFirst().get();
        assertThat(propertyNotSold.getSold()).isFalse();

        // when
        propertyService.setSoldProperty(propertyNotSold.getId());

        // then
        var propertySold = propertyRepository.findById(propertyNotSold.getId()).get();
        assertThat(propertySold.getSold()).isTrue();

    }

    @Test
    void shouldUpdateRoomsInPropertyInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        var roomXlId = initDatabaseByRoom(RoomsType.ROOM_XL, BigDecimal.TEN, user);
        var roomLId = initDatabaseByRoom(RoomsType.ROOM_L, new BigDecimal("8"), user);
        var roomMId = initDatabaseByRoom(RoomsType.ROOM_M, new BigDecimal("4"), user);

        initDatabaseByProperty(user, roomXlId, roomMId);

        var property = propertyService.findAllProperties(false).stream().findFirst().get();

        var containsRoomsXLAndM = property.getRooms().stream()
                .map(it -> it.getId())
                .collect(Collectors.toList())
                .containsAll(asList(roomXlId, roomMId));
        assertThat(containsRoomsXLAndM).isTrue();

        var newRoomsLAndM = roomsService.getAll().stream()
                .filter(it -> it.getId().equals(roomLId)
                        || it.getId().equals(roomMId))
                .collect(Collectors.toList());
        property.setRooms(newRoomsLAndM);

        // when
        propertyService.updateProperty(property);

        // then
        var entity = propertyRepository.findAll().stream().findFirst().get();
        assertThat(entity.getRooms()).hasSize(2);
        var containsRoomsLAndM = property.getRooms().stream()
                .map(it -> it.getId())
                .collect(Collectors.toList())
                .containsAll(asList(roomLId, roomMId));
        assertThat(containsRoomsLAndM).isTrue();

    }

    @Test
    void shouldAddOneRoomToTwoProperties() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByProperty(user);
        initDatabaseByProperty(user);
        var roomId = initDatabaseByRoom(RoomsType.ROOM_XL, BigDecimal.TEN, user);
        var roomToSet = RoomsDto.builder()
                .id(roomId)
                .build();

        var allProperties = propertyService.findAllProperties(false);
        assertThat(allProperties).hasSize(2);

        var firstProperty = allProperties.get(0);
        var secondProperty = allProperties.get(1);

        firstProperty.setRooms(asList(roomToSet));
        secondProperty.setRooms(asList(roomToSet));

        // when
        propertyService.updateProperty(firstProperty);
        propertyService.updateProperty(secondProperty);

        // then
        var propertiesAfterUpdate = propertyService.findAllProperties(false);
        assertThat(propertiesAfterUpdate).hasSize(2);
        assertThat(propertiesAfterUpdate.get(0).getRooms())
                .isEqualTo(propertiesAfterUpdate.get(1).getRooms());

    }
}
