package com.forbusypeople.budget.services.integrations;


import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.enums.RoomsType;
import com.forbusypeople.budget.repositories.*;
import com.forbusypeople.budget.repositories.entities.*;
import com.forbusypeople.budget.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @Autowired
    protected CyclicalExpensesService cyclicalExpensesService;
    @Autowired
    protected CyclicalExpensesRepository cyclicalExpensesRepository;
    @Autowired
    protected ExpensesEstimatePercentageRepository expensesEstimatePercentageRepository;
    @Autowired
    protected ExpensesEstimatePercentageService expensesEstimatePercentageService;

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
        var assetEntity = AssetEntity.builder()
                .incomeDate(date)
                .user(userEntity)
                .amount(BigDecimal.ONE)
                .category(category)
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
        AssetEntity entity1 = AssetEntity.builder()
                .amount(new BigDecimal(1))
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();
        AssetEntity entity2 = AssetEntity.builder()
                .amount(new BigDecimal(3))
                .incomeDate(Instant.now())
                .category(AssetCategory.SALARY)
                .user(userEntity)
                .build();
        AssetEntity entity3 = AssetEntity.builder()
                .amount(new BigDecimal(5))
                .incomeDate(Instant.now())
                .category(AssetCategory.RENT)
                .user(userEntity)
                .build();

        assetsRepository.saveAll(asList(entity1, entity2, entity3));
    }

    protected void initDatabaseBySecondMockUserAndHisAssets() {
        var userEntity = initDatabaseBySecondUser();
        AssetEntity entity1 = AssetEntity.builder()
                .amount(new BigDecimal(1))
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();
        AssetEntity entity2 = AssetEntity.builder()
                .amount(new BigDecimal(3))
                .incomeDate(Instant.now())
                .category(AssetCategory.SALARY)
                .user(userEntity)
                .build();
        AssetEntity entity3 = AssetEntity.builder()
                .amount(new BigDecimal(5))
                .incomeDate(Instant.now())
                .category(AssetCategory.RENT)
                .user(userEntity)
                .build();

        assetsRepository.saveAll(asList(entity1, entity2, entity3));
    }

    protected UUID initDatabaseByExpenses(UserEntity user) {
        var expenses = ExpensesEntity.builder()
                .user(user)
                .amount(BigDecimal.ONE)
                .build();

        var entity = expensesRepository.save(expenses);
        return entity.getId();
    }

    protected UUID initDatabaseByExpenses(UserEntity user,
                                          String date) {
        var dateSuffix = "T00:00:00.001Z";

        var expenses = ExpensesEntity.builder()
                .user(user)
                .amount(BigDecimal.ONE)
                .purchaseDate(Instant.parse(date + dateSuffix))
                .build();

        var entity = expensesRepository.save(expenses);
        return entity.getId();
    }

    protected void initDatabaseByProperty(UserEntity user) {
        initDatabaseByProperty(user, null);
    }

    protected void initDatabaseByProperty(UserEntity user,
                                          UUID... roomsId) {
        var postCode = "00-010";
        var city = "Warsaw";
        var street = "Smerfetki";
        var house = "12A";
        var single = false;

        var roomsIdForEntity = Objects.isNull(roomsId)
                ? null
                : Arrays.stream(roomsId).collect(Collectors.toList());
        var roomsEntity = Objects.isNull(roomsIdForEntity)
                ? null
                : roomsRepository.findAllById(roomsIdForEntity);

        PropertyEntity property = PropertyEntity.builder()
                .postCode(postCode)
                .city(city)
                .street(street)
                .house(house)
                .single(single)
                .user(user)
                .sold(false)
                .rooms(roomsEntity)
                .build();

        propertyRepository.save(property);
    }

    protected UUID initDatabaseByRoom(RoomsType type,
                                      BigDecimal cost,
                                      UserEntity user) {
        var entity = RoomsEntity.builder()
                .user(user)
                .cost(cost)
                .type(type)
                .build();
        var savedEntity = roomsRepository.save(entity);

        return savedEntity.getId();
    }

    protected void initDatabaseByCyclicalExpenses(UserEntity user) {
        var entity = CyclicalExpensesEntity.builder()
                .day(21)
                .category(ExpensesCategory.FUN)
                .month(MonthsEnum.JANUARY)
                .amount(BigDecimal.ONE)
                .user(user)
                .build();

        cyclicalExpensesRepository.save(entity);

    }

    protected void initDatabaseByEstimatedExpenses(UserEntity user) {
        var entities = asList(
                ExpensesEstimatedPercentageEntity.builder()
                        .percentage(new BigDecimal("25"))
                        .user(user)
                        .category(ExpensesCategory.OTHERS)
                        .build(),
                ExpensesEstimatedPercentageEntity.builder()
                        .percentage(new BigDecimal("25"))
                        .user(user)
                        .category(ExpensesCategory.FOR_LIFE)
                        .build(),
                ExpensesEstimatedPercentageEntity.builder()
                        .percentage(new BigDecimal("25"))
                        .user(user)
                        .category(ExpensesCategory.EDUCATION)
                        .build(),
                ExpensesEstimatedPercentageEntity.builder()
                        .percentage(new BigDecimal("25"))
                        .user(user)
                        .category(ExpensesCategory.FUN)
                        .build()
        );

        expensesEstimatePercentageRepository.saveAll(entities);
    }
}
