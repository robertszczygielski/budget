package com.forbusypeople.budget.services;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SuggestedAssetsService {

    private final PropertyService propertyService;

    public List<AssetDto> getAllRentRooms() {
        var properties = propertyService.findAllProperties(false);
        return properties.stream()
                .map(property -> property.getRooms().stream()
                        .map(room -> AssetDto.builder()
                                .category(AssetCategory.RENT)
                                .amount(room.getCost())
                                .description(getDescription(property))
                                .build())
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private String getDescription(PropertyDto property) {
        return "City: " + property.getCity() +
                " Street: " + property.getStreet() +
                " House: " + property.getHouse();
    }
}
