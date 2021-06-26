package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.RoomsType;
import com.forbusypeople.budget.services.dtos.RoomsDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class RoomsServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveOneRoomInToDatabase() {
        // given
        initDatabaseByPrimeUser();
        var savedType = RoomsType.ROOM_L;
        RoomsDto dto = RoomsDto.builder()
                .type(savedType)
                .cost(BigDecimal.TEN)
                .build();

        // when
        var savedId = roomsService.saveOrUpdate(dto);

        // then
        assertThat(savedId).isNotNull();
        var entityOptional = roomsRepository.findById(savedId);
        var entity = entityOptional.get();
        assertThat(entity.getType()).isEqualTo(savedType);

    }

    @Test
    void shouldUpdateRoomDataInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        var type = RoomsType.ROOM_XL;
        var oldCost = BigDecimal.ONE;
        var newCost = BigDecimal.TEN;
        var roomId = initDatabaseByRoom(type, oldCost, user);

        var dotToChange = RoomsDto.builder()
                .id(roomId)
                .cost(newCost)
                .type(type)
                .build();

        // when
        roomsService.saveOrUpdate(dotToChange);

        // then
        var entityOptional = roomsRepository.findById(roomId);
        var entity = entityOptional.get();
        assertThat(entity.getCost()).isEqualTo(newCost);

    }

    @Test
    void shouldMakeRoomTypeInactive() {
        // given
        var user = initDatabaseByPrimeUser();
        var roomId = initDatabaseByRoom(RoomsType.ROOM_L, new BigDecimal("2"), user);

        // when
        roomsService.inactiveRoom(roomId);

        // then
        var entityOptional = roomsRepository.findById(roomId);
        var entity = entityOptional.get();
        assertThat(entity.getCost()).isEqualTo(BigDecimal.ZERO);

    }

    @Test
    void shouldGetAllRoomsWithNonCostIfThereIsNoRoomInDatabase() {
        // given
        initDatabaseByPrimeUser();

        // when
        var allRooms = roomsService.getAll();

        // then
        assertThat(allRooms).hasSize(6);
        assertThat(allRooms.get(0).getCost()).isEqualTo(BigDecimal.ZERO);

    }

    @Test
    void shouldGetAllRoomsWithSomeCostIfThereIsSomeRoomInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        Arrays.asList(RoomsType.values())
                .forEach(roomsType -> initDatabaseByRoom(roomsType, BigDecimal.TEN, user));

        // when
        var allRooms = roomsService.getAll();

        // then
        assertThat(allRooms).hasSize(6);
        assertThat(allRooms.get(0).getCost()).isEqualTo(BigDecimal.TEN);

    }
}
