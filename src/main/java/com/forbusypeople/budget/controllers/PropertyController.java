package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.PropertyService;
import com.forbusypeople.budget.services.dtos.PropertyDto;
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
    public void setProperty(@RequestBody PropertyDto dto) {
        propertyService.addProperty(dto);
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
