package com.forbusypeople.budget.services.integrations;


import com.forbusypeople.budget.builders.AssetEntityBuilder;
import com.forbusypeople.budget.builders.ExpensesEntityBuilder;
import com.forbusypeople.budget.builders.PropertyEntityBuilder;
import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.repositories.*;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.*;
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
    @Autowired
    protected PropertyService propertyService;
    @Autowired
    protected PropertyRepository propertyRepository;
    @Autowired
    protected RoomsService roomsService;
    @Autowired
    protected RoomsRepository roomsRepository;

    protected static final String USER_NAME_PRIME = "userNamePrime";
    protected static final String USER_PASSWORD_PRIME = "userPasswordPrime";
    protected static final String USER_NAME_SECOND = "userNameSecond";
    protected final String USER_PASSWORD_SECOND = "userPasswordSecond";

    protected void initDatabaseByAssetsForUser(UserEntity userEntity,
                                               String date) {
        var suffixDate = "T00:00:00.001Z";
        initDatabaseByAssetsForUser(userEntity,
                                    Instant.parse(date + suffixDate),
                                    AssetCategory.BONUS
        );
    }

    protected void initDatabaseByAssetsForUser(UserEntity userEntity,
                                               String date,
                                               AssetCategory category) {
        var suffixDate = "T00:00:00.001Z";
        initDatabaseByAssetsForUser(userEntity,
                                    Instant.parse(date + suffixDate),
                                    category
        );
    }

    protected void initDatabaseByAssetsForUser(UserEntity userEntity) {
        initDatabaseByAssetsForUser(userEntity,
                                    Instant.now(),
                                    AssetCategory.BONUS
        );
    }

    private void initDatabaseByAssetsForUser(UserEntity userEntity,
                                             Instant date,
                                             AssetCategory category) {
        var assetEntity = new AssetEntityBuilder()
                .withIncomeDate(date)
                .withUser(userEntity)
                .withAmount(BigDecimal.ONE)
                .withCategory(category)
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

    protected UUID initDatabaseByExpenses(UserEntity user,
                                          String date) {
        var dateSuffix = "T00:00:00.001Z";

        var expenses = new ExpensesEntityBuilder()
                .withUser(user)
                .withAmount(BigDecimal.ONE)
                .withPurchaseDate(Instant.parse(date + dateSuffix))
                .build();

        var entity = expensesRepository.save(expenses);
        return entity.getId();
    }

    protected void initDatabaseByProperty(UserEntity user) {
        var postCode = "00-010";
        var city = "Warsaw";
        var street = "Smerfetki";
        var house = "12A";
        var single = false;
        var rooms = 3;
        PropertyEntity property = new PropertyEntityBuilder()
                .withPostCode(postCode)
                .withCity(city)
                .withStreet(street)
                .withHouse(house)
                .withSingle(single)
                .withRooms(rooms)
                .withUser(user)
                .build();

        propertyRepository.save(property);
    }
}
