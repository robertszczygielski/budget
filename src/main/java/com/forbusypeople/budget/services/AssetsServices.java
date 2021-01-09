package com.forbusypeople.budget.services;

import com.forbusypeople.budget.services.dtos.AssetsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Arrays.asList;

@Service
public class AssetsServices {

    private AssetsDto assetsDto = new AssetsDto();

    public AssetsDto getAllAssets() {
        return assetsDto;
    }

    public void setAsset(int asset) {
        var allAssets = assetsDto.getAssets();
        if (allAssets == null) {
            allAssets = new ArrayList<>();
        }
        allAssets.add(asset);
        assetsDto.setAssets(allAssets);
    }
}
