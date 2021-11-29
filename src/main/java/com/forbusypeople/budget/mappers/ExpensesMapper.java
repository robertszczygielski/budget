package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpensesMapper {

    @Mapping(source = "dto.id", target = "id")
    @Mapping(target = "maintenance", expression = "java(false)")
    ExpensesEntity formDtoToEntity(ExpensesDto dto,
                                   UserEntity user);

    ExpensesDto formEntityToDto(ExpensesEntity entity);

    List<ExpensesDto> fromEntitiesToDtos(List<ExpensesEntity> allExpenses);
}
