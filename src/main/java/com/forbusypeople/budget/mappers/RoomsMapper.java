package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.RoomsEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.RoomsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomsMapper {

    @Mapping(source = "dto.id", target = "id")
    RoomsEntity fromDtoToEntity(RoomsDto dto,
                                UserEntity user);
}
