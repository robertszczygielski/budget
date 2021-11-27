package com.forbusypeople.budget.services.integrations.properties;

import com.forbusypeople.budget.services.dtos.HousingMaintenanceExpensesDto;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import com.forbusypeople.budget.services.integrations.InitIntegrationTestData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.forbusypeople.budget.services.integrations.properties.InitPropertyData.getPropertyDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PropertiesWithMaintenanceExpensesIT extends InitIntegrationTestData {

    @Test
    void shouldSavePropertyWithMaintenanceExpenses() {
        // given
        var user = initDatabaseByPrimeUser();
        var categoryName = "Some category";
        initDatabaseByHousingMaintenanceCategory(categoryName, user);
        var categoryDto = housingMaintenanceCategoryService.findAll(user).get(0);
        var housingMaintenance = HousingMaintenanceExpensesDto.builder()
                .category(categoryDto)
                .amount(BigDecimal.TEN)
                .purchaseDate(Instant.now())
                .description("desc")
                .build();

        var housingMaintenances = List.of(
                housingMaintenance
        );

        PropertyDto dto = getPropertyDto();
        dto.setHousingMaintenances(housingMaintenances);

        // when
        propertyService.saveProperty(user, dto);

        // then
        var propertyEntity = propertyRepository.findAllByUser(user, false);
        assertThat(propertyEntity.size()).isEqualTo(1);
        assertThat(
                propertyEntity.get(0).getHousingMaintenances().get(0).getCategory().getName())
                .isEqualTo(
                        categoryName);

    }
}
