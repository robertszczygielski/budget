package com.forbusypeople.budget.services;

import com.forbusypeople.budget.builders.AssetDtoBuilder;
import com.forbusypeople.budget.builders.AssetEntityBuilder;
import com.forbusypeople.budget.enums.ValidatorsAssetEnum;
import com.forbusypeople.budget.excetpions.AssetIncompleteException;
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

    private AssetValidator assetValidator = new AssetValidator();
    private AssetsMapper assetsMapper = new AssetsMapper();

    private AssetsService service;

    @BeforeEach
    public void init() {
        service = new AssetsService(assetsRepository, assetsMapper, assetValidator);
    }

    @Test
    void shouldReturnListWithOneElementIfThereIsOneElementInDatabase() {
        // given
        var asset = BigDecimal.ONE;
        AssetEntity assetEntity = new AssetEntityBuilder()
                .withAmount(asset)
                .build();
        List<AssetEntity> assetList = Collections.singletonList(assetEntity);
        Mockito.when(assetsRepository.findAll()).thenReturn(assetList);

        // when
        var result = service.getAllAssets();

        // then
        Assertions.assertThat(result)
                .hasSize(1)
                .contains(new AssetDtoBuilder().withAmount(asset).build());
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

        Mockito.when(assetsRepository.findAll()).thenReturn(assetsEntity);

        // when
        var result = service.getAllAssets();

        // then
        Assertions.assertThat(result)
                .hasSize(2)
                .containsExactly(
                        new AssetDtoBuilder().withAmount(assetOne).build(),
                        new AssetDtoBuilder().withAmount(assetTwo).build()
                );
    }

    @Test
    void shouldVerifyIfTheRepositorySaveWasCalledOneTime() {
        // given
        BigDecimal asset = BigDecimal.ONE;
        AssetDto dto = new AssetDtoBuilder()
                .withAmount(asset)
                .build();
        AssetEntity entity = new AssetEntityBuilder()
                .withAmount(asset)
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
        var dto = new AssetDtoBuilder().withAmount(asset).build();
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
        AssetDto dto = new AssetDto();

        // when
        var result = assertThrows(AssetIncompleteException.class,
                () -> service.setAsset(dto));

        // then
        assertEquals(ValidatorsAssetEnum.NO_AMOUNT.getMessage(), result.getMessage());

    }
}