package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.AdditionalUserDataEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.AdditionalUserDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdditionalUserDataMapper {

    @Mapping(source = "dto.id", target = "id")
    AdditionalUserDataEntity fromDtoToEntity(AdditionalUserDataDto dto,
                                             UserEntity user);

    AdditionalUserDataDto fromEntityToDto(AdditionalUserDataEntity entity);

}
