package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.RoomsType;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
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
        initDatabaseByPrimeUser();
        PropertyDto property = PropertyDto.builder()
                .postCode(postCode)
                .city(city)
                .street(street)
                .house(house)
                .single(single)
                .build();

        // when
        var result = propertyService.addProperty(property);

        // then
        assertThat(result).isNotNull();
        var entityList = propertyRepository.findAll();
        assertThat(entityList).hasSize(1);
        var entity = entityList.get(0);
        assertAll(
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

}
