package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.CyclicalExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.CyclicalExpensesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CyclicalExpensesMapper {

    default List<CyclicalExpensesEntity> formDtoToEntityList(List<CyclicalExpensesDto> dtos,
                                                             UserEntity user) {
        return dtos.stream()
                .map(dto -> formDtoToEntity(dto, user))
                .collect(Collectors.toList());
    }

    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "user", target = "user")
    CyclicalExpensesEntity formDtoToEntity(CyclicalExpensesDto dto,
                                           UserEntity user);

    List<CyclicalExpensesDto> formEntityToDtoList(List<CyclicalExpensesEntity> entities);
}
