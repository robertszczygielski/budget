package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.builders.ExpensesDtoBuilder;
import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.FilterExpensesParametersEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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
    void shouldReturnAllExpensesSavedInDatabaseAfter() {
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
}
