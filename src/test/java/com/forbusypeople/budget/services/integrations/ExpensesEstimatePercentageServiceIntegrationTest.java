package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.ExpensesCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ExpensesEstimatePercentageServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveDataForEstimatePercentage() {
        // given
        initDatabaseByPrimeUser();
        Map<ExpensesCategory, BigDecimal> estination = new HashMap<>() {{
            put(ExpensesCategory.FUN, new BigDecimal("25"));
            put(ExpensesCategory.OTHERS, new BigDecimal("25"));
            put(ExpensesCategory.FOR_LIFE, new BigDecimal("25"));
            put(ExpensesCategory.EDUCATION, new BigDecimal("25"));
        }};

        // when
        expensesEstimatePercentageService.saveEstimation(estination);

        // then
        var allEntities = expensesEstimatePercentageRepository.findAll();
        var entityAsList = StreamSupport
                .stream(allEntities.spliterator(), false)
                .map(it -> it.getCategory())
                .collect(Collectors.toList());
        assertThat(entityAsList).hasSize(4)
                .contains(
                        ExpensesCategory.OTHERS,
                        ExpensesCategory.FUN,
                        ExpensesCategory.FOR_LIFE,
                        ExpensesCategory.EDUCATION
                );

    }

    @Test
    void shouldGetMapWithCategoryAndEstimationExpensesValueIfThereAreDateInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByEstimatedExpenses(user);

        // when
        var resultMap = expensesEstimatePercentageService.getEstimation();

        // then
        assertThat(resultMap).hasSize(4);

    }

    @Test
    void shouldGetMapWithCategoryAndEstimationZeroIfThereAreNoValueInDatabase() {
        // given
        initDatabaseByPrimeUser();

        // when
        var resultMap = expensesEstimatePercentageService.getEstimation();

        // then
        assertAll(
                () -> assertThat(resultMap).hasSize(4),
                () -> assertThat(resultMap.get(ExpensesCategory.FUN)).isEqualTo(BigDecimal.ZERO)
        );

    }
}