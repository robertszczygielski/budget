package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.builders.ExpensesDtoBuilder;
import com.forbusypeople.budget.enums.ExpensesCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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

}
