package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.services.dtos.AdditionalUserDataDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AdditionalUserDataServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveDataInDatabase() {
        // given
        initDatabaseByPrimeUser();
        var mail = "mail@mail.com";
        var dto = AdditionalUserDataDto.builder()
                .email(mail)
                .build();

        // when
        additionalUserDataService.saveAdditionalData(dto);

        // then
        var entities = additionalUserDataRepository.findAll();
        var entity = entities.iterator().next();
        assertThat(entity.getEmail()).isEqualTo(mail);

    }
}
