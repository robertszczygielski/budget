package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.RoomsType;
import com.forbusypeople.budget.services.dtos.RoomsDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
        var savedId = roomsService.saveRoom(dto);

        // then
        assertThat(savedId).isNotNull();
        var entityOptional = roomsRepository.findById(savedId);
        var entity = entityOptional.get();
        assertThat(entity.getType()).isEqualTo(savedType);

    }
}
