package com.forbusypeople.budget.services;

import com.forbusypeople.budget.mappers.AssetsMapper;
import com.forbusypeople.budget.repositories.AssetsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AssetsServicesTest {

    private AssetsRepository assetsRepository = new FakeAssetsRepository();
    private AssetsMapper assetsMapper = new AssetsMapper();

    @Test
    void shouldSaveAssetAndReturnListWithOneElementIfThereWasNoSavedAssetsBefore() {
        // given
        var asset = 1;
        var service = new AssetsServices(assetsRepository, assetsMapper);
        service.setAsset(asset);

        // when
        var result = service.getAllAssets();

        // then
        var listOfAss = result.getAssets();
        Assertions.assertThat(listOfAss)
                .hasSize(1)
                .containsExactly(asset);
    }

    @Test
    void shouldSaveAssetAndReturnListWithTwoElementsIfThereWasNoSavedAssetsBefore() {
        // given
        var assetOne = 1;
        var assetTwo = 2;
        var service = new AssetsServices(assetsRepository, assetsMapper);
        service.setAsset(assetOne);
        service.setAsset(assetTwo);

        // when
        var result = service.getAllAssets();

        // then
        var listOfAss = result.getAssets();
        Assertions.assertThat(listOfAss)
                .hasSize(2)
                .containsExactly(assetOne, assetTwo);
    }
}