package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    PropertyDto fromEntityToDto(PropertyEntity entity);

    @Mapping(source = "dto.id", target = "id")
    PropertyEntity fromDtoToEntity(PropertyDto dto,
                                   UserEntity user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntityByDto(@MappingTarget PropertyEntity entity,
                           PropertyDto dto);
}
