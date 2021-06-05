package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.builders.AssetDtoBuilder;
import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.enums.FilterExceptionErrorMessages;
import com.forbusypeople.budget.enums.FilterParametersEnum;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.excetpions.MissingAssetsFilterException;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.AssetDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.util.Streams;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssetServiceIntegrationTest extends InitIntegrationTestData {

    @Test
    void shouldReturnListWithThreeElements() {
        // given
        initDatabaseByDefaultMockUserAndHisAssets();
        initDatabaseBySecondMockUserAndHisAssets();

        // when
        var allAssetsInDB = assetsService.getAllAssets();

        // then
        assertThat(allAssetsInDB).hasSize(3);

    }

    @Test
    void shouldAddAssetToDB() {
        // given
        initDatabaseByPrimeUser();
        String desc = "some desc";
        AssetDto dto = new AssetDtoBuilder()
                .withAmount(new BigDecimal(11))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.BONUS)
                .withDescription(desc)
                .build();

        // when
        assetsService.setAsset(dto);

        // then
        var allAssetInDB = assetsRepository.findAll();
        assertThat(allAssetInDB).hasSize(1);
        var entity = allAssetInDB.get(0);
        assertThat(entity.getCategory()).isEqualTo(dto.getCategory());
        assertThat(entity.getAmount()).isEqualTo(dto.getAmount());
        assertThat(entity.getIncomeDate()).isEqualTo(dto.getIncomeDate());
        assertThat(entity.getDescription()).isEqualTo(desc);

    }

    @Test
    void shouldReturnListOnlyWithOneCategory() {
        // given
        initDatabaseByDefaultMockUserAndHisAssets();
        var category = AssetCategory.OTHER;

        // when
        var allAssetsWithOneCategory = assetsService.getAssetsByCategory(category);

        // then
        assertThat(allAssetsWithOneCategory).hasSize(1);
        var entity = allAssetsWithOneCategory.get(0);
        assertThat(entity.getCategory()).isEqualTo(category);
    }

    @Test
    void shouldDeleteAllAssetsOfChosenUser() {
        // given
        initDatabaseByDefaultMockUserAndHisAssets();
        initDatabaseBySecondMockUserAndHisAssets();
        int numberOfAllAssets = 6;
        int numberOfLeaveAssets = 3;

        var allUsers = userRepository.findAll();
        var userToDeleteAssets = Streams.stream(allUsers).findFirst();
        UserEntity userEntity = userToDeleteAssets.get();
        var userToLeaveAssets = Streams.stream(allUsers)
                .filter(entity -> !entity.equals(userEntity))
                .findFirst().get();

        var allAssetsInDatabase = assetsRepository.findAll();
        assertThat(allAssetsInDatabase).hasSize(numberOfAllAssets);

        // when
        assetsService.deleteAssetByUser(userEntity);

        // then
        var assetsAfterDelete = assetsRepository.findAll();
        assertThat(assetsAfterDelete).hasSize(numberOfLeaveAssets);

        var assetsUserId = assetsAfterDelete.stream()
                .map(assetEntity -> assetEntity.getUser())
                .map(ue -> ue.getId())
                .collect(Collectors.toSet());
        assertThat(assetsUserId).hasSize(1)
                .containsExactly(userToLeaveAssets.getId());

    }

    @Test
    void shouldGetAllAssetsByFilterByDateFromTo() {
        // given
        var fromDate = "2021-01-02";
        var toDate = "2021-01-22";
        var middleDate = "2021-01-10";
        var outOfRangeDate = "2021-02-01";
        var user = initDatabaseByPrimeUser();
        initDatabaseByAssetsForUser(user, fromDate);
        initDatabaseByAssetsForUser(user, toDate);
        initDatabaseByAssetsForUser(user, middleDate);
        initDatabaseByAssetsForUser(user, outOfRangeDate);

        Map<String, String> filter = new HashMap<>() {{
            put(FilterParametersEnum.FROM_DATE.getKey(), fromDate);
            put(FilterParametersEnum.TO_DATE.getKey(), toDate);
        }};

        // when
        var result = assetsService.getAssetsByFilter(filter);

        // then
        assertThat(result).hasSize(3);
        var dateAsString = result.stream()
                .map(dto -> dto.getIncomeDate().toString().substring(0, fromDate.length()))
                .collect(Collectors.toList());
        assertThat(dateAsString).hasSize(3)
                .contains(fromDate, toDate, middleDate)
                .doesNotContain(outOfRangeDate);
    }

    @Test
    void shouldGetAllAssetsByFilterByDateFromToAndCategory() {
        // given
        var lookingCategory = AssetCategory.BONUS;
        var notLookingCategory = AssetCategory.SALARY;
        var fromDate = "2021-01-02";
        var toDate = "2021-01-22";
        var middleDate = "2021-01-10";
        var outOfRangeDate = "2021-02-01";
        var user = initDatabaseByPrimeUser();
        initDatabaseByAssetsForUser(user, fromDate, notLookingCategory);
        initDatabaseByAssetsForUser(user, toDate, notLookingCategory);
        initDatabaseByAssetsForUser(user, middleDate, lookingCategory);
        initDatabaseByAssetsForUser(user, outOfRangeDate, lookingCategory);

        Map<String, String> filter = new HashMap<>() {{
            put(FilterParametersEnum.FROM_DATE.getKey(), fromDate);
            put(FilterParametersEnum.TO_DATE.getKey(), toDate);
            put(FilterParametersEnum.CATEGORY.getKey(), lookingCategory.name());
        }};

        // when
        var result = assetsService.getAssetsByFilter(filter);

        // then
        assertThat(result).hasSize(1);
        var dateAsString = result.stream()
                .map(dto -> dto.getIncomeDate().toString().substring(0, fromDate.length()))
                .collect(Collectors.toList());
        assertThat(dateAsString).hasSize(1)
                .contains(middleDate)
                .doesNotContain(fromDate, toDate, outOfRangeDate);
    }

    @Test
    void shouldGetAllAssetsByFilterByMonthYear() {
        // given
        var fromDate = "2021-01-02";
        var toDate = "2021-01-22";
        var middleDate = "2021-01-10";
        var outOfRangeDate = "2021-02-01";
        var user = initDatabaseByPrimeUser();
        initDatabaseByAssetsForUser(user, fromDate);
        initDatabaseByAssetsForUser(user, toDate);
        initDatabaseByAssetsForUser(user, middleDate);
        initDatabaseByAssetsForUser(user, outOfRangeDate);

        Map<String, String> filter = new HashMap<>() {{
            put(FilterParametersEnum.MONTH.getKey(), MonthsEnum.JANUARY.name());
            put(FilterParametersEnum.YEAR.getKey(), "2021");
        }};

        // when
        var result = assetsService.getAssetsByFilter(filter);

        // then
        assertThat(result).hasSize(3);
        var dateAsString = result.stream()
                .map(dto -> dto.getIncomeDate().toString().substring(0, fromDate.length()))
                .collect(Collectors.toList());
        assertThat(dateAsString).hasSize(3)
                .contains(fromDate, toDate, middleDate)
                .doesNotContain(outOfRangeDate);
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource
    void shouldThrowExceptionWhenOneOfTheFiltersKey(String testName,
                                                    ParameterTestData testData) {
        // given
        initDatabaseByPrimeUser();

        // when
        var result = assertThrows(MissingAssetsFilterException.class,
                                  () -> assetsService.getAssetsByFilter(testData.filter)
        );

        // then
        AssertionsForClassTypes.assertThat(result.getMessage())
                .isEqualTo(FilterExceptionErrorMessages.MISSING_ASSETS_FILTER_KEY.getMessage(
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
