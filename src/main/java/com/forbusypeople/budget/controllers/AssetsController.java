package com.forbusypeople.budget.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    @GetMapping
    public Dto getAssets() {
        var dto = new Dto();
        dto.setAssets(asList(1, 3, 5));
        return dto;
    }

    private class Dto {
        private List<Integer> assets;

        public List<Integer> getAssets() {
            return assets;
        }

        public void setAssets(List<Integer> assets) {
            this.assets = assets;
        }
    }
}
