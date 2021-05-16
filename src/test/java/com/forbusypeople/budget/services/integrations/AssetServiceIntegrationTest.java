package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.builders.AssetDtoBuilder;
import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.enums.FilterExceptionErrorMessages;
import com.forbusypeople.budget.enums.FilterParametersCalendarEnum;
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
        AssetDto dto = new AssetDtoBuilder()
                .withAmount(new BigDecimal(11))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.BONUS)
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
            put(FilterParametersCalendarEnum.FROM_DATE.getKey(), fromDate);
            put(FilterParametersCalendarEnum.TO_DATE.getKey(), toDate);
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
            put(FilterParametersCalendarEnum.MONTH.getKey(), MonthsEnum.JANUARY.name());
            put(FilterParametersCalendarEnum.YEAR.getKey(), "2021");
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
                Arguments.of("test for missing " + FilterParametersCalendarEnum.FROM_DATE.getKey(),
                             new ParameterTestData(
                                     new HashMap<>() {{
                                         put(FilterParametersCalendarEnum.TO_DATE.getKey(), "2020-02-20");
                                     }},
                                     FilterParametersCalendarEnum.FROM_DATE
                             )
                ),

                Arguments.of("test for missing " + FilterParametersCalendarEnum.TO_DATE.getKey(),
                             new ParameterTestData(
                                     new HashMap<>() {{
                                         put(FilterParametersCalendarEnum.FROM_DATE.getKey(), "2020-02-20");
                                     }},
                                     FilterParametersCalendarEnum.TO_DATE
                             )
                ),

                Arguments.of("test for missing " + FilterParametersCalendarEnum.MONTH.getKey(),
                             new ParameterTestData(
                                     new HashMap<>() {{
                                         put(FilterParametersCalendarEnum.MONTH.getKey(), "january");
                                     }},
                                     FilterParametersCalendarEnum.YEAR
                             )
                ),

                Arguments.of("test for missing " + FilterParametersCalendarEnum.YEAR.getKey(),
                             new ParameterTestData(
                                     new HashMap<>() {{
                                         put(FilterParametersCalendarEnum.YEAR.getKey(), "2020");
                                     }},
                                     FilterParametersCalendarEnum.MONTH
                             )
                )

        );

    }

    private static class ParameterTestData {
        public Map<String, String> filter;
        public FilterParametersCalendarEnum missingKey;

        public ParameterTestData(Map<String, String> filter,
                                 FilterParametersCalendarEnum missingKey) {
            this.filter = filter;
            this.missingKey = missingKey;
        }
    }

}
