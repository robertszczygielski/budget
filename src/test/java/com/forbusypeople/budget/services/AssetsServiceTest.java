package com.forbusypeople.budget.services;

import com.forbusypeople.budget.enums.ValidatorsAssetEnum;
import com.forbusypeople.budget.excetpions.AssetIncompleteException;
import com.forbusypeople.budget.feign.dto.FeignNbpDto;
import com.forbusypeople.budget.feign.dto.RatesNbpDto;
import com.forbusypeople.budget.filters.FilterRangeStrategy;
import com.forbusypeople.budget.mappers.AssetsMapper;
import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.services.currency.CurrencyService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.users.UserLogInfoService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssetsServiceTest {

    @Mock
    private AssetsRepository assetsRepository;
    @Mock
    private UserLogInfoService userLogInfoService;
    @Mock
    private FilterRangeStrategy filterRangeStrategy;
    @Mock
    private CurrencyService currencyService;

    private final AssetValidator assetValidator = new AssetValidator();
    private final AssetsMapper assetsMapper = new AssetsMapper();

    private AssetsService service;

    @BeforeEach
    public void init() {
        service = new AssetsService(assetsRepository,
                                    assetsMapper,
                                    assetValidator,
                                    userLogInfoService,
                                    filterRangeStrategy,
                                    currencyService
        );
    }

    @Test
    void shouldReturnListWithOneElementIfThereIsOneElementInDatabase() {
        // given
        var asset = BigDecimal.ONE;
        AssetEntity assetEntity = AssetEntity.builder()
                .amount(asset)
                .build();
        List<AssetEntity> assetList = Collections.singletonList(assetEntity);
        when(assetsRepository.getAssetEntitiesByUser(any())).thenReturn(assetList);

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
        AssetEntity entityOne = AssetEntity.builder()
                .amount(assetOne)
                .build();
        AssetEntity entityTwo = AssetEntity.builder()
                .amount(assetTwo)
                .build();
        List<AssetEntity> assetsEntity = asList(entityOne, entityTwo);

        when(assetsRepository.getAssetEntitiesByUser(any())).thenReturn(assetsEntity);

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
        AssetEntity entity = AssetEntity.builder()
                .amount(asset)
                .incomeDate(incomeDate)
                .build();

        // when
        service.setAsset(asList(dto));

        // then
        Mockito.verify(assetsRepository, Mockito.times(1)).save(entity);

    }

    @Test
    void shouldVerifyIfTheRepositoryUpdateWasCalled() {
        // given
        BigDecimal asset = BigDecimal.ONE;
        var dto = AssetDto.builder().amount(asset).build();
        var entity = AssetEntity.builder().amount(asset).build();
        when(assetsRepository.findById(any())).thenReturn(Optional.of(entity));

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
                                  () -> service.setAsset(asList(dto))
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
                                  () -> service.setAsset(asList(dto))
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
                                  () -> service.setAsset(asList(dto))
        );

        // then
        assertEquals(expectedMessage, result.getMessage());

    }

    @Test
    void shouldSaveAssetsWithCurrenciesAfterChangeAmountToCurrentCurrency() {
        // given
        String currencyUSD = "USD";
        Instant incomeDate = Instant.now();
        var userAmount = BigDecimal.ONE;
        var nbpAmount = BigDecimal.TEN;
        var amountInDatabase = BigDecimal.TEN;

        List<AssetDto> dots = List.of(AssetDto.builder()
                                              .amount(userAmount)
                                              .incomeDate(incomeDate)
                                              .currencyCode(currencyUSD)
                                              .build());

        AssetEntity assetEntity = AssetEntity.builder()
                .incomeDate(incomeDate)
                .amount(amountInDatabase)
                .build();

        FeignNbpDto currencyDto = FeignNbpDto.builder()
                .rates(List.of(RatesNbpDto.builder()
                                       .mid(nbpAmount)
                                       .build()))
                .build();

        when(currencyService.getCurrencyFromNbp(currencyUSD)).thenReturn(currencyDto);

        // when
        service.setAsset(dots);

        // then
        Mockito.verify(assetsRepository).save(assetEntity);

    }
}