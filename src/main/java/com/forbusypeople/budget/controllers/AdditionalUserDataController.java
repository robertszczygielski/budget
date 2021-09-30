package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.dtos.AdditionalUserDataDto;
import com.forbusypeople.budget.services.users.AdditionalUserDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/additional/data")
@AllArgsConstructor
public class AdditionalUserDataController {

    private final AdditionalUserDataService additionalUserDataService;

    @PostMapping
    private void saveAdditionalData(@RequestBody AdditionalUserDataDto dto) {
        additionalUserDataService.saveAdditionalData(dto);
    }

}
