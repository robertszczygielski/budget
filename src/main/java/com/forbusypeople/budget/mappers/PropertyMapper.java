package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.builders.PropertyDtoBuilder;
import com.forbusypeople.budget.builders.PropertyEntityBuilder;
import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PropertyMapper {
    public PropertyDto fromEntityToDto(PropertyEntity entity) {
        PropertyDtoBuilder builderDto = new PropertyDtoBuilder();

        if (Objects.nonNull(entity.getStreet())) {
            builderDto.withStreet(entity.getStreet());
        }
        if (Objects.nonNull(entity.getId())) {
            builderDto.withId(entity.getId());
        }
        if (Objects.nonNull(entity.getSingle())) {
            builderDto.withSingle(entity.getSingle());
        }
        if (Objects.nonNull(entity.getPostCode())) {
            builderDto.withPostCode(entity.getPostCode());
        }
        if (Objects.nonNull(entity.getRooms())) {
            builderDto.withRooms(entity.getRooms());
        }
        if (Objects.nonNull(entity.getHouse())) {
            builderDto.withHouse(entity.getHouse());
        }

        return builderDto.build();
    }

    public PropertyEntity fromDtoToEntity(PropertyDto dto,
                                          UserEntity user) {
        PropertyEntityBuilder entityBuilder = new PropertyEntityBuilder();

        if (Objects.nonNull(user)) {
            entityBuilder.withUser(user);
        }
        if (Objects.nonNull(dto.getStreet())) {
            entityBuilder.withStreet(dto.getStreet());
        }
        if (Objects.nonNull(dto.getId())) {
            entityBuilder.withId(dto.getId());
        }
        if (Objects.nonNull(dto.getSingle())) {
            entityBuilder.withSingle(dto.getSingle());
        }
        if (Objects.nonNull(dto.getPostCode())) {
            entityBuilder.withPostCode(dto.getPostCode());
        }
        if (Objects.nonNull(dto.getRooms())) {
            entityBuilder.withRooms(dto.getRooms());
        }
        if (Objects.nonNull(dto.getHouse())) {
            entityBuilder.withHouse(dto.getHouse());
        }

        return entityBuilder.build();
    }
}
