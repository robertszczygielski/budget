package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.services.dtos.CyclicalExpensesDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class CyclicalExpensesServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveOneCyclicalExpenseInDatabase() {
        // given
        initDatabaseByPrimeUser();
        CyclicalExpensesDto dto = CyclicalExpensesDto.builder()
                .amount(BigDecimal.ONE)
                .category(ExpensesCategory.FUN)
                .day(21)
                .month(MonthsEnum.JANUARY)
                .build();

        // when
        cyclicalExpensesService.saveCyclicalExpenses(asList(dto));

        // then
        var savedEntities = cyclicalExpensesRepository.findAll();
        var savedEntity = savedEntities.get(0);
        assertThat(savedEntity.getAmount()).isEqualTo(BigDecimal.ONE);

    }

    @Test
    void shouldGetAllCyclicalExpensesFromDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByCyclicalExpenses(user);

        // when
        var allCyclicalExpenses = cyclicalExpensesService.getAllCyclicalExpenses();

        // then
        assertThat(allCyclicalExpenses).hasSize(1);
        var savedEntity = allCyclicalExpenses.get(0);
        assertThat(savedEntity.getCategory()).isEqualTo(ExpensesCategory.FUN);

    }
}