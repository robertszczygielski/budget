package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.dtos.AssetsDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsService assetsService;

    public AssetsController(AssetsService assetsService) {
        this.assetsService = assetsService;
    }

    @GetMapping
    public AssetsDto getAssets() {
        return assetsService.getAllAssets();
    }

    @PostMapping("/{asset}")
    public void  setAsset(@PathVariable("asset") int asset) {
        assetsService.setAsset(asset);
    }

}
