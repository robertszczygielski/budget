package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.HousingMaintenanceCategoryEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.HousingMaintenanceCategoryDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HousingMaintenanceCategoryMapper {

    @Mapping(source = "dto.id", target = "id")
    @Mapping(target = "user", expression = "java(user)")
    HousingMaintenanceCategoryEntity fromDtoToEntity(HousingMaintenanceCategoryDto dto,
                                                     @Context UserEntity user);

    HousingMaintenanceCategoryDto fromEntityToDto(HousingMaintenanceCategoryEntity entity);

}
