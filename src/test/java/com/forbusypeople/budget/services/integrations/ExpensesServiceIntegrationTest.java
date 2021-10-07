package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.FilterExceptionErrorMessages;
import com.forbusypeople.budget.enums.FilterParametersEnum;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.excetpions.MissingExpensesFilterException;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpensesServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldSaveOneExpensesInToDatabase() {
        // given
        initDatabaseByPrimeUser();
        var dto = ExpensesDto.builder()
                .amount(BigDecimal.ONE)
                .build();

        // when
        expensesService.setExpenses(dto);

        // then
        var entitiesInDatabase = expensesRepository.findAll();
        assertThat(entitiesInDatabase).hasSize(1);
        assertThat(entitiesInDatabase.get(0).getAmount()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void shouldSaveOneFullInfillExpensesInToDatabase() {
        // given
        initDatabaseByPrimeUser();
        var amount = BigDecimal.ONE;
        var purchaseDate = Instant.parse("2020-09-09T08:08:01.001Z");
        var category = ExpensesCategory.EDUCATION;
        var description = "some expenses description";
        var dto = ExpensesDto.builder()
                .amount(amount)
                .purchaseDate(purchaseDate)
                .category(category)
                .description(description)
                .build();

        // when
        expensesService.setExpenses(dto);

        // then
        var entitiesInDatabase = expensesRepository.findAll();
        assertThat(entitiesInDatabase).hasSize(1);
        assertAll(
                () -> assertThat(entitiesInDatabase.get(0).getAmount()).isEqualTo(BigDecimal.ONE),
                () -> assertThat(entitiesInDatabase.get(0).getDescription()).isEqualTo(description),
                () -> assertThat(entitiesInDatabase.get(0).getPurchaseDate()).isEqualTo(purchaseDate),
                () -> assertThat(entitiesInDatabase.get(0).getCategory()).isEqualTo(category)
        );
    }

    @Test
    void shouldDeleteExpensesFromDatabase() {
        // given
        var user = initDatabaseByPrimeUser();
        var expensesId = initDatabaseByExpenses(user);
        var dto = ExpensesDto.builder()
                .amount(BigDecimal.ONE)
                .id(expensesId)
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
        var dto = ExpensesDto.builder()
                .amount(BigDecimal.TEN)
                .category(ExpensesCategory.EDUCATION)
                .id(expenseId)
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
            put(FilterParametersEnum.FROM_DATE.getKey(), fromDate);
            put(FilterParametersEnum.TO_DATE.getKey(), toDate);
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
            put(FilterParametersEnum.MONTH.getKey(), MonthsEnum.JANUARY.name());
            put(FilterParametersEnum.YEAR.getKey(), "2021");
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
    void shouldThrowExceptionWhenOneOfTheFiltersKey(String testName,
                                                    ParameterTestData testData) {
        // given
        initDatabaseByPrimeUser();

        // when
        var result = assertThrows(MissingExpensesFilterException.class,
                                  () -> expensesService.getFilteredExpenses(testData.filter)
        );

        // then
        assertThat(result.getMessage())
                .isEqualTo(FilterExceptionErrorMessages.MISSING_EXPENSES_FILTER_KEY.getMessage(
                        testData.missingKey.getKey()));

    }

    private static Stream<Arguments> shouldThrowExceptionWhenOneOfTheFiltersKey() {
        return Stream.of(
                Arguments.of("test for missing " + FilterParametersEnum.FROM_DATE.getKey(),
                             new ParameterTestData(
                                     new HashMap<>() {{
                                         put(FilterParametersEnum.TO_DATE.getKey(), "2020-02-20");
                                     }},
                                     FilterParametersEnum.FROM_DATE
                             )
                ),

                Arguments.of("test for missing " + FilterParametersEnum.TO_DATE.getKey(),
                             new ParameterTestData(
                                     new HashMap<>() {{
                                         put(FilterParametersEnum.FROM_DATE.getKey(), "2020-02-20");
                                     }},
                                     FilterParametersEnum.TO_DATE
                             )
                ),

                Arguments.of("test for missing " + FilterParametersEnum.MONTH.getKey(),
                             new ParameterTestData(
                                     new HashMap<>() {{
                                         put(FilterParametersEnum.MONTH.getKey(), "january");
                                     }},
                                     FilterParametersEnum.YEAR
                             )
                ),

                Arguments.of("test for missing " + FilterParametersEnum.YEAR.getKey(),
                             new ParameterTestData(
                                     new HashMap<>() {{
                                         put(FilterParametersEnum.YEAR.getKey(), "2020");
                                     }},
                                     FilterParametersEnum.MONTH
                             )
                )

        );

    }

    private static class ParameterTestData {
        public Map<String, String> filter;
        public FilterParametersEnum missingKey;

        public ParameterTestData(Map<String, String> filter,
                                 FilterParametersEnum missingKey) {
            this.filter = filter;
            this.missingKey = missingKey;
        }
    }
}
