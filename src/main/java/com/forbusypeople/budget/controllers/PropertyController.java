package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.aspects.annotations.SetLoggedUser;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import com.forbusypeople.budget.services.properties.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public List<PropertyDto> getAllNotSoldProperty() {
        return propertyService.findAllProperties(false);
    }

    @GetMapping("/sold")
    public List<PropertyDto> getAllSoldProperty() {
        return propertyService.findAllProperties(true);
    }

    @PostMapping
    @SetLoggedUser
    public void setProperty(UserEntity user,
                            @RequestBody PropertyDto dto) {
        propertyService.saveProperty(user, dto);
    }

    @PutMapping
    public void updateProperty(@RequestBody PropertyDto dto) {
        propertyService.updateProperty(dto);
    }

    @DeleteMapping("/{id}")
    private void setSoldProperty(@PathVariable UUID id) {
        propertyService.setSoldProperty(id);
    }

}
