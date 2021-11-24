package com.forbusypeople.budget.services.properties;

import com.forbusypeople.budget.mappers.HousingMaintenanceCategoryMapper;
import com.forbusypeople.budget.repositories.HousingMaintenanceCategoryRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceCategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HousingMaintenanceCategoryService {

    private final HousingMaintenanceCategoryRepository housingMaintenanceCategoryRepository;
    private final HousingMaintenanceCategoryMapper housingMaintenanceCategoryMapper;

    public void saveCategory(UserEntity user,
                             HousingMaintenanceCategoryDto dto) {
        var entity = housingMaintenanceCategoryMapper.fromDtoToEntity(dto, user);
        housingMaintenanceCategoryRepository.save(entity);
    }

    public List<HousingMaintenanceCategoryDto> findAll(UserEntity user) {
        var allEntities = housingMaintenanceCategoryRepository.findAllByUser(user);
        return allEntities.stream()
                .map(housingMaintenanceCategoryMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
