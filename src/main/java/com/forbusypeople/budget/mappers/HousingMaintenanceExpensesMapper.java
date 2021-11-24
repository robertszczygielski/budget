package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.HousingMaintenanceExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceExpensesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HousingMaintenanceExpensesMapper {

    @Mapping(source = "dto.id", target = "id")
    HousingMaintenanceExpensesEntity formDtoToEntity(HousingMaintenanceExpensesDto dto,
                                                     UserEntity user);

    HousingMaintenanceExpensesDto formEntityToDto(HousingMaintenanceExpensesEntity entity);

}
