package com.forbusypeople.budget.services;

import com.forbusypeople.budget.builders.AssetEntityBuilder;
import com.forbusypeople.budget.enums.ValidatorsAssetEnum;
import com.forbusypeople.budget.excetpions.AssetIncompleteException;
import com.forbusypeople.budget.filters.FilterRangeStrategy;
import com.forbusypeople.budget.mappers.AssetsMapper;
import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.validators.AssetValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AssetsServiceTest {

    @Mock
    private AssetsRepository assetsRepository;
    @Mock
    private UserLogInfoService userLogInfoService;
    @Mock
    private FilterRangeStrategy filterRangeStrategy;

    private final AssetValidator assetValidator = new AssetValidator();
    private final AssetsMapper assetsMapper = new AssetsMapper();

    private AssetsService service;

    @BeforeEach
    public void init() {
        service = new AssetsService(assetsRepository,
                                    assetsMapper,
                                    assetValidator,
                                    userLogInfoService,
                                    filterRangeStrategy
        );
    }

    @Test
    void shouldReturnListWithOneElementIfThereIsOneElementInDatabase() {
        // given
        var asset = BigDecimal.ONE;
        AssetEntity assetEntity = new AssetEntityBuilder()
                .withAmount(asset)
                .build();
        List<AssetEntity> assetList = Collections.singletonList(assetEntity);
        Mockito.when(assetsRepository.getAssetEntitiesByUser(any())).thenReturn(assetList);

        // when
        var result = service.getAllAssets();

        // then
        Assertions.assertThat(result)
                .hasSize(1)
                .contains(AssetDto.builder().amount(asset).build());
    }

    @Test
    void shouldReturnListWithTwoElementsIfThereWasTwoElementsInDatabase() {
        // given
        var assetOne = BigDecimal.ONE;
        var assetTwo = new BigDecimal("2");
        AssetEntity entityOne = new AssetEntityBuilder()
                .withAmount(assetOne)
                .build();
        AssetEntity entityTwo = new AssetEntityBuilder()
                .withAmount(assetTwo)
                .build();
        List<AssetEntity> assetsEntity = asList(entityOne, entityTwo);

        Mockito.when(assetsRepository.getAssetEntitiesByUser(any())).thenReturn(assetsEntity);

        // when
        var result = service.getAllAssets();

        // then
        Assertions.assertThat(result)
                .hasSize(2)
                .containsExactly(
                        AssetDto.builder().amount(assetOne).build(),
                        AssetDto.builder().amount(assetTwo).build()
                );
    }

    @Test
    void shouldVerifyIfTheRepositorySaveWasCalledOneTime() {
        // given
        BigDecimal asset = BigDecimal.ONE;
        Instant incomeDate = Instant.now();
        AssetDto dto = AssetDto.builder()
                .amount(asset)
                .incomeDate(incomeDate)
                .build();
        AssetEntity entity = new AssetEntityBuilder()
                .withAmount(asset)
                .withIncomeDate(incomeDate)
                .build();

        // when
        service.setAsset(dto);

        // then
        Mockito.verify(assetsRepository, Mockito.times(1)).save(entity);

    }

    @Test
    void shouldVerifyIfTheRepositoryUpdateWasCalled() {
        // given
        BigDecimal asset = BigDecimal.ONE;
        var dto = AssetDto.builder().amount(asset).build();
        var entity = new AssetEntityBuilder().withAmount(asset).build();
        Mockito.when(assetsRepository.findById(any())).thenReturn(Optional.of(entity));

        // when
        service.updateAsset(dto);

        // then
        Mockito.verify(assetsRepository, Mockito.times(1)).saveAndFlush(entity);
    }

    @Test
    void shouldThrowExceptionWhenAmountInAssetDtoIsNull() {
        // given
        AssetDto dto = AssetDto.builder()
                .incomeDate(Instant.now())
                .build();

        // when
        var result = assertThrows(AssetIncompleteException.class,
                                  () -> service.setAsset(dto)
        );

        // then
        assertEquals(ValidatorsAssetEnum.NO_AMOUNT.getMessage(), result.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenIncomeDateInAssetDtoIsNull() {
        // given
        AssetDto dto = AssetDto.builder()
                .amount(BigDecimal.ONE)
                .build();

        // when
        var result = assertThrows(AssetIncompleteException.class,
                                  () -> service.setAsset(dto)
        );

        // then
        assertEquals(ValidatorsAssetEnum.NO_INCOME_DATE.getMessage(), result.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenIncomeDateAndAmountInAssetDtoIsNull() {
        // given
        AssetDto dto = new AssetDto();
        String messageSeparator = "; ";
        String expectedMessage = ValidatorsAssetEnum.NO_AMOUNT.getMessage()
                + messageSeparator
                + ValidatorsAssetEnum.NO_INCOME_DATE.getMessage();

        // when
        var result = assertThrows(AssetIncompleteException.class,
                                  () -> service.setAsset(dto)
        );

        // then
        assertEquals(expectedMessage, result.getMessage());

    }
}