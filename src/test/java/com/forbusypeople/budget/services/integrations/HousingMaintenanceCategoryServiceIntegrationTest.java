package com.forbusypeople.budget.services.integrations;


import com.forbusypeople.budget.services.dtos.HousingMaintenanceCategoryDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HousingMaintenanceCategoryServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveCategory() {
        // given
        var user = initDatabaseByPrimeUser();
        String someCategory = "some category";
        var dto = HousingMaintenanceCategoryDto.builder()
                .name(someCategory)
                .build();

        // when
        housingMaintenanceCategoryService.saveCategory(user, dto);

        // then
        var entity = housingMaintenanceCategoryRepository.findAll();
        assertThat(entity.size()).isEqualTo(1);
        assertThat(entity.get(0).getName()).isEqualTo(someCategory);

    }
}
