package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.builders.ExpensesDtoBuilder;
import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.ExpensesExceptionErrorMessages;
import com.forbusypeople.budget.enums.FilterExpensesParametersEnum;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.excetpions.MissingExpensesFilterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpensesServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveOneExpensesInToDatabase() {
        // given
        initDatabaseByPrimeUser();
        var dto = new ExpensesDtoBuilder()
                .withAmount(BigDecimal.ONE)
                .build();

        // when
        expensesService.setExpenses(dto);

        // then
        var entitiesInDatabase = expensesRepository.findAll();
        assertThat(entitiesInDatabase).hasSize(1);
        assertThat(entitiesInDatabase.get(0).getAmount()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void shouldDeleteExpensesFromDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        var expensesId = initDatabaseByExpenses(user);
        var dto = new ExpensesDtoBuilder()
                .withAmount(BigDecimal.ONE)
                .withId(expensesId)
                .build();

        var entitiesInDatabase = expensesRepository.findAll();
        assertThat(entitiesInDatabase).hasSize(1);

        // when
        expensesService.deleteExpenses(dto);

        // then
        var entityInDatabaseAfterDelete = expensesRepository.findAll();
        assertThat(entityInDatabaseAfterDelete).hasSize(0);


    }

    @Test
    void shouldUpdateExpensesInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        var expenseId = initDatabaseByExpenses(user);
        var dto = new ExpensesDtoBuilder()
                .withAmount(BigDecimal.TEN)
                .withCategory(ExpensesCategory.EDUCATION)
                .withId(expenseId)
                .build();

        var entityInDatabase = expensesRepository.findById(expenseId);
        var entity = entityInDatabase.get();
        assertThat(entity.getAmount()).isEqualTo(BigDecimal.ONE);
        assertThat(entity.getCategory()).isNull();

        // when
        expensesService.updateExpenses(dto);

        // then
        var entityInDatabaseAfterUpdate = expensesRepository.findById(expenseId);
        var entityAfterUpdate = entityInDatabaseAfterUpdate.get();
        assertThat(entityAfterUpdate.getAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(entityAfterUpdate.getCategory()).isEqualTo(ExpensesCategory.EDUCATION);

    }

    @Test
    void shouldReturnAllExpensesSavedInDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByExpenses(user);
        initDatabaseByExpenses(user);

        var secondUser = initDatabaseBySecondUser();
        initDatabaseByExpenses(secondUser);

        // when
        var result = expensesService.getAllExpenses();

        // then
        assertThat(result).hasSize(2);

    }

    @Test
    void shouldReturnAllExpensesSavedInDatabaseFilteredByDate() {
        // given
        var fromDate = "2021-01-04";
        var toDate = "2021-01-10";
        var middleDate = "2021-01-08";
        var notInRangeDate = "2021-01-11";
        var user = initDatabaseByPrimeUser();
        initDatabaseByExpenses(user, fromDate);
        initDatabaseByExpenses(user, toDate);
        initDatabaseByExpenses(user, middleDate);
        initDatabaseByExpenses(user, notInRangeDate);
        Map<String, String> filter = new HashMap<>() {{
            put(FilterExpensesParametersEnum.FROM_DATE.getKey(), fromDate);
            put(FilterExpensesParametersEnum.TO_DATE.getKey(), toDate);
        }};

        // when
        var result = expensesService.getFilteredExpenses(filter);

        // then
        assertThat(result).hasSize(3);
        var dateAsString = result.stream()
                .map(dto -> dto.getPurchaseDate().toString().substring(0, fromDate.length()))
                .collect(Collectors.toSet());
        assertThat(dateAsString)
                .contains(fromDate, toDate, middleDate)
                .doesNotContain(notInRangeDate);

    }

    @Test
    void shouldReturnAllExpensesSavedInDatabaseFilteredByMonthAndYear() {
        // given
        var fromDate = "2021-01-04";
        var toDate = "2021-01-10";
        var middleDate = "2021-01-08";
        var notInRangeDate = "2021-02-11";
        var user = initDatabaseByPrimeUser();
        initDatabaseByExpenses(user, fromDate);
        initDatabaseByExpenses(user, toDate);
        initDatabaseByExpenses(user, middleDate);
        initDatabaseByExpenses(user, notInRangeDate);
        Map<String, String> filter = new HashMap<>() {{
            put(FilterExpensesParametersEnum.MONTH.getKey(), MonthsEnum.JANUARY.name());
            put(FilterExpensesParametersEnum.YEAR.getKey(), "2021");
        }};

        // when
        var result = expensesService.getFilteredExpenses(filter);

        // then
        assertThat(result).hasSize(3);
        var dateAsString = result.stream()
                .map(dto -> dto.getPurchaseDate().toString().substring(0, fromDate.length()))
                .collect(Collectors.toSet());
        assertThat(dateAsString)
                .contains(fromDate, toDate, middleDate)
                .doesNotContain(notInRangeDate);

    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    void shouldThrowExceptionWhenOneOfTheFiltersKey(String testName, ParameterTestData testData) {
        // given
        // when
        var result = assertThrows(MissingExpensesFilterException.class,
                () -> expensesService.getFilteredExpenses(testData.filter));

        // then
        assertThat(result.getMessage())
                .isEqualTo(ExpensesExceptionErrorMessages.MISSING_FILTER_KEY.getMessage() + testData.missingKey.getKey());

    }

    private static Stream<Arguments> shouldThrowExceptionWhenOneOfTheFiltersKey() {
        return Stream.of(
                Arguments.of("test for missing " + FilterExpensesParametersEnum.FROM_DATE.getKey(),
                        new ParameterTestData(
                                new HashMap<>() {{
                                    put(FilterExpensesParametersEnum.TO_DATE.getKey(), "2020-02-20");
                                }},
                                FilterExpensesParametersEnum.FROM_DATE)
                ),

                Arguments.of("test for missing " + FilterExpensesParametersEnum.TO_DATE.getKey(),
                        new ParameterTestData(
                                new HashMap<>() {{
                                    put(FilterExpensesParametersEnum.FROM_DATE.getKey(), "2020-02-20");
                                }},
                                FilterExpensesParametersEnum.TO_DATE)
                ),

                Arguments.of("test for missing " + FilterExpensesParametersEnum.MONTH.getKey(),
                        new ParameterTestData(
                                new HashMap<>() {{
                                    put(FilterExpensesParametersEnum.MONTH.getKey(), "january");
                                }},
                                FilterExpensesParametersEnum.YEAR)
                ),

                Arguments.of("test for missing " + FilterExpensesParametersEnum.YEAR.getKey(),
                        new ParameterTestData(
                                new HashMap<>() {{
                                    put(FilterExpensesParametersEnum.YEAR.getKey(), "2020");
                                }},
                                FilterExpensesParametersEnum.MONTH)
                )

        );

    }

    private static class ParameterTestData {
        public Map<String, String> filter;
        public FilterExpensesParametersEnum missingKey;

        public ParameterTestData(Map<String, String> filter, FilterExpensesParametersEnum missingKey) {
            this.filter = filter;
            this.missingKey = missingKey;
        }
    }
}
