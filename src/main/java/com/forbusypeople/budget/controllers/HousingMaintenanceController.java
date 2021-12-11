package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.aspects.annotations.SetLoggedUser;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceCategoryDto;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceExpensesDto;
import com.forbusypeople.budget.services.properties.HousingMaintenanceCategoryService;
import com.forbusypeople.budget.services.properties.HousingMaintenanceExpensesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/maintenance/housing")
@AllArgsConstructor
public class HousingMaintenanceController {

    private final HousingMaintenanceCategoryService housingMaintenanceCategoryService;
    private final HousingMaintenanceExpensesService housingMaintenanceExpensesService;

    @PostMapping("/expenses/{house}")
    @SetLoggedUser
    public void setHousingMaintenanceExpenses(
            UserEntity user,
            @RequestBody
                    HousingMaintenanceExpensesDto dto,
            @PathVariable("house") UUID propertyId
    ) {
        housingMaintenanceExpensesService.saveExpenses(user, dto, propertyId);
    }

    @PostMapping("/category")
    @SetLoggedUser
    public void setHousingMaintenanceCategory(
            UserEntity user,
            @RequestBody
                    HousingMaintenanceCategoryDto dto
    ) {
        housingMaintenanceCategoryService.saveCategory(user, dto);
    }

    @GetMapping("/category")
    @SetLoggedUser
    public List<HousingMaintenanceCategoryDto> getHousingMaintenanceCategory(UserEntity user) {
        return housingMaintenanceCategoryService.findAll(user);
    }

}
