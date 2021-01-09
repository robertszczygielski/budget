package com.forbusypeople.budget.services;

import com.forbusypeople.budget.services.dtos.AssetsDto;
import org.springframework.stereotype.Service;

import static java.util.Arrays.asList;

@Service
public class AssetsServices {

    public AssetsDto getAllAssets() {
        var assetsDto = new AssetsDto();
        assetsDto.setAssets(asList(1, 3, 5));
        return assetsDto;
    }

}
