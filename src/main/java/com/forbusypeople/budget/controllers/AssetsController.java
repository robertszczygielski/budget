package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.AssetsServices;
import com.forbusypeople.budget.services.dtos.AssetsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsServices assetsServices;

    public AssetsController(AssetsServices assetsServices) {
        this.assetsServices = assetsServices;
    }

    @GetMapping
    public AssetsDto getAssets() {
        return assetsServices.getAllAssets();
    }

}
