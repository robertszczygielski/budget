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

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        var asset = 1;
        AssetEntity assetEntity = new AssetEntityBuilder()
                .withAmount(new BigDecimal(asset))
                .build();
        List<AssetEntity> assetList = Collections.singletonList(assetEntity);
        Mockito.when(assetsRepository.findAll()).thenReturn(assetList);

        // when
        var result = service.getAllAssets();

        // then
        var listOfAss = result.getAssets();
        Assertions.assertThat(listOfAss)
                .hasSize(1)
                .containsExactly(asset);
    }

    @Test
    void shouldReturnListWithTwoElementsIfThereWasTwoElementsInDatabase() {
        // given
        var assetOne = 1;
        var assetTwo = 2;
        AssetEntity entityOne = new AssetEntityBuilder()
                .withAmount(new BigDecimal(assetOne))
                .build();
        AssetEntity entityTwo = new AssetEntityBuilder()
                .withAmount(new BigDecimal(assetTwo))
                .build();
        List<AssetEntity> assetsEntity = asList(entityOne, entityTwo);

        Mockito.when(assetsRepository.findAll()).thenReturn(assetsEntity);

        // when
        var result = service.getAllAssets();

        // then
        var listOfAss = result.getAssets();
        Assertions.assertThat(listOfAss)
                .hasSize(2)
                .containsExactly(assetOne, assetTwo);
    }

    @Test
    void shouldVerityIfTheRepositorySaveWasCalledOneTime() {
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