package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.builders.AssetDtoBuilder;
import com.forbusypeople.budget.builders.AssetEntityBuilder;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetsMapper {

    public AssetEntity fromDtoToEntity(AssetDto dto) {

        if (Objects.isNull(dto)) {
            return null;
        }

        var entityBuilder = new AssetEntityBuilder();

        if (Objects.nonNull(dto.getAmount())) {
            entityBuilder.withAmount(dto.getAmount());
        }

        if (Objects.nonNull(dto.getId())) {
            entityBuilder.withId(dto.getId());
        }

        if (Objects.nonNull(dto.getIncomeDate())) {
            entityBuilder.withIncomeDate(dto.getIncomeDate());
        }

        return entityBuilder.build();

    }

    public AssetDto fromEntityToDto(AssetEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        var dtoBuilder = new AssetDtoBuilder();

        if (Objects.nonNull(entity.getAmount())) {
            dtoBuilder.withAmount(entity.getAmount());
        }

        if (Objects.nonNull(entity.getId())) {
            dtoBuilder.withId(entity.getId());
        }

        if (Objects.nonNull(entity.getIncomeDate())) {
            dtoBuilder.withIncomeDate(entity.getIncomeDate());
        }

        return dtoBuilder.build();

    }

}
