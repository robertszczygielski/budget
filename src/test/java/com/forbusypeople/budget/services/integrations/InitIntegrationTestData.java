package com.forbusypeople.budget.services.integrations;


import com.forbusypeople.budget.builders.AssetEntityBuilder;
import com.forbusypeople.budget.builders.ExpensesEntityBuilder;
import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.repositories.ExpensesRepository;
import com.forbusypeople.budget.repositories.UserRepository;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.JWTService;
import com.forbusypeople.budget.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static java.util.Arrays.asList;

@SpringBootTest
@Transactional
@WithMockUser(username = "userNamePrime", password = "userPasswordPrime")
public abstract class InitIntegrationTestData {

    @Autowired
    protected ExpensesService expensesService;
    @Autowired
    protected ExpensesRepository expensesRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected AssetsRepository assetsRepository;
    @Autowired
    protected AssetsService assetsService;
    @Autowired
    protected UserDetailsServiceImpl userDetailsService;
    @Autowired
    protected JWTService jwtService;
    @Autowired
    protected AuthenticationManager authenticationManager;

    protected static final String USER_NAME_PRIME = "userNamePrime";
    protected static final String USER_PASSWORD_PRIME = "userPasswordPrime";
    protected static final String USER_NAME_SECOND = "userNameSecond";
    protected final String USER_PASSWORD_SECOND = "userPasswordSecond";

    protected void initDatabaseByAssetsForUser(UserEntity userEntity) {
        var assetEntity = new AssetEntityBuilder()
                .withIncomeDate(Instant.now())
                .withUser(userEntity)
                .withAmount(BigDecimal.ONE)
                .withCategory(AssetCategory.BONUS)
                .build();

        assetsRepository.save(assetEntity);
    }

    protected UserEntity initDatabaseByPrimeUser() {
        UserEntity entity = new UserEntity();
        entity.setPassword(USER_PASSWORD_PRIME);
        entity.setUsername(USER_NAME_PRIME);

        return userRepository.save(entity);
    }

    protected UserEntity initDatabaseBySecondUser() {
        var user = new UserEntity();
        user.setUsername(USER_NAME_SECOND);
        user.setPassword(USER_PASSWORD_SECOND);

        return userRepository.save(user);
    }

    protected void initDatabaseByDefaultMockUserAndHisAssets() {
        var userEntity = initDatabaseByPrimeUser();
        AssetEntity entity1 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(1))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.OTHER)
                .withUser(userEntity)
                .build();
        AssetEntity entity2 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(3))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.SALARY)
                .withUser(userEntity)
                .build();
        AssetEntity entity3 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(5))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.RENT)
                .withUser(userEntity)
                .build();

        assetsRepository.saveAll(asList(entity1, entity2, entity3));
    }

    protected void initDatabaseBySecondMockUserAndHisAssets() {
        var userEntity = initDatabaseBySecondUser();
        AssetEntity entity1 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(1))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.OTHER)
                .withUser(userEntity)
                .build();
        AssetEntity entity2 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(3))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.SALARY)
                .withUser(userEntity)
                .build();
        AssetEntity entity3 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(5))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.RENT)
                .withUser(userEntity)
                .build();

        assetsRepository.saveAll(asList(entity1, entity2, entity3));
    }

    protected UUID initDatabaseByExpenses(UserEntity user) {
        var expenses = new ExpensesEntityBuilder()
                .withUser(user)
                .withAmount(BigDecimal.ONE)
                .build();

        var entity = expensesRepository.save(expenses);
        return entity.getId();
    }

}
