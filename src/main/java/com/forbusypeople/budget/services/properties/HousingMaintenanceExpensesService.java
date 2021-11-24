package com.forbusypeople.budget.services.properties;

import com.forbusypeople.budget.mappers.HousingMaintenanceExpensesMapper;
import com.forbusypeople.budget.repositories.HousingMaintenanceExpensesRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceExpensesDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HousingMaintenanceExpensesService {

    private final HousingMaintenanceExpensesRepository housingMaintenanceExpensesRepository;
    private final HousingMaintenanceExpensesMapper housingMaintenanceExpensesMapper;

    public void saveExpenses(UserEntity user,
                             HousingMaintenanceExpensesDto dto) {
        var entity = housingMaintenanceExpensesMapper.formDtoToEntity(dto, user);
        housingMaintenanceExpensesRepository.save(entity);
    }

}
