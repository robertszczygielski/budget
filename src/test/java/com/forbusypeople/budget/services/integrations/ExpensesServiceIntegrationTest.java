package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.builders.ExpensesDtoBuilder;
import com.forbusypeople.budget.builders.ExpensesEntityBuilder;
import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.repositories.ExpensesRepository;
import com.forbusypeople.budget.repositories.UserRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.ExpensesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@Transactional
@WithMockUser(username = "user123", password = "userPassword")
public class ExpensesServiceIntegrationTest {

    @Autowired
    private ExpensesService expensesService;
    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private UserRepository userRepository;

    public static final String USER_NAME = "user123";
    public static final String USER_PASSWORD = "userPassword";

    @Test
    void shouldSaveOneExpensesInToDatabase() {
        // given
        initUserInDatabase();
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
        var user = initUserInDatabase();
        var expensesId = initExpensesInDatabase(user);
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
        var user = initUserInDatabase();
        var expenseId = initExpensesInDatabase(user);
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
        var user = initUserInDatabase();
        initExpensesInDatabase(user);
        initExpensesInDatabase(user);

        var secondUser = initSecondUserInDatabase();
        initExpensesInDatabase(secondUser);

        // when
        var result = expensesService.getAllExpenses();

        // then
        assertThat(result).hasSize(2);

    }

    private UUID initExpensesInDatabase(UserEntity user) {
        var expenses = new ExpensesEntityBuilder()
                .withUser(user)
                .withAmount(BigDecimal.ONE)
                .build();

        var entity = expensesRepository.save(expenses);
        return entity.getId();
    }

    private UserEntity initUserInDatabase() {
        var user = new UserEntity();
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);

        return userRepository.save(user);
    }

    private UserEntity initSecondUserInDatabase() {
        var user = new UserEntity();
        user.setUsername("secondUser");
        user.setPassword(USER_PASSWORD);

        return userRepository.save(user);
    }
}
