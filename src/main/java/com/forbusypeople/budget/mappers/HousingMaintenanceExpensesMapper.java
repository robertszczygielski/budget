package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.HousingMaintenanceExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceExpensesDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {HousingMaintenanceCategoryMapper.class})
public interface HousingMaintenanceExpensesMapper {

    @Mapping(source = "dto.id", target = "id")
    @Mapping(target = "user", expression = "java(user)")
    HousingMaintenanceExpensesEntity formDtoToEntity(HousingMaintenanceExpensesDto dto,
                                                     @Context UserEntity user);

    HousingMaintenanceExpensesDto formEntityToDto(HousingMaintenanceExpensesEntity entity);

}
