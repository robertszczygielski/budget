package com.forbusypeople.budget.services.properties;

import com.forbusypeople.budget.mappers.HousingMaintenanceCategoryMapper;
import com.forbusypeople.budget.repositories.HousingMaintenanceCategoryRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceCategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

}
