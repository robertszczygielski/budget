package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.services.dtos.HousingMaintenanceExpensesDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HousingMaintenanceExpensesServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveOneExpenses() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByHousingMaintenanceCategory("some cat", user);
        var categoryDto = housingMaintenanceCategoryService.findAll(user);
        var purchaseDate = Instant.now();
        var description = "some desc";
        var dto = HousingMaintenanceExpensesDto.builder()
                .amount(BigDecimal.ONE)
                .purchaseDate(purchaseDate)
                .description(description)
                .category(categoryDto.get(0))
                .build();

        // when
        housingMaintenanceExpensesService.saveExpenses(user, dto);

        // then
        var entity = housingMaintenanceExpensesRepository.findAll();
        assertThat(entity.size()).isEqualTo(1);
        assertAll(
                () -> assertThat(entity.get(0).getPurchaseDate()).isEqualTo(purchaseDate),
                () -> assertThat(entity.get(0).getDescription()).isEqualTo(description),
                () -> assertThat(entity.get(0).getCategory().getName()).isEqualTo("some cat")
        );

    }
}
