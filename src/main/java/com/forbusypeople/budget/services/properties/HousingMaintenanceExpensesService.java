package com.forbusypeople.budget.services.properties;

import com.forbusypeople.budget.mappers.HousingMaintenanceExpensesMapper;
import com.forbusypeople.budget.repositories.HousingMaintenanceExpensesRepository;
import com.forbusypeople.budget.repositories.PropertyRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceExpensesDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Service
public class HousingMaintenanceExpensesService {

    private final HousingMaintenanceExpensesRepository housingMaintenanceExpensesRepository;
    private final HousingMaintenanceExpensesMapper housingMaintenanceExpensesMapper;
    private final PropertyRepository propertyRepository;
    private final ExpensesService expensesService;

    @Transactional
    public void saveExpenses(UserEntity user,
                             HousingMaintenanceExpensesDto dto,
                             UUID propertyId) {
        var entity =
                housingMaintenanceExpensesMapper.formDtoToEntity(dto, user);
        expensesService.saveExpensesForHousingMaintenance(user, dto.getAmount());
        var savedEntity = housingMaintenanceExpensesRepository.save(entity);

        var property = propertyRepository
                .findAllByUser(user, false)
                .stream()
                .filter(it -> it.getId().equals(propertyId))
                .findFirst().get();
        if (Objects.isNull(property.getHousingMaintenances())) {
            property.setHousingMaintenances(new ArrayList<>());
        }
        var propertyMaintenance = property.getHousingMaintenances();
        propertyMaintenance.add(savedEntity);
        property.setHousingMaintenances(propertyMaintenance);
        propertyRepository.save(property);

    }

}
