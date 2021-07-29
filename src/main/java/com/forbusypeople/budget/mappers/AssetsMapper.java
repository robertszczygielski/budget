package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetsMapper {

    public AssetEntity fromDtoToEntity(AssetDto dto,
                                       UserEntity user) {

        if (Objects.isNull(dto)) {
            return null;
        }

        var entityBuilder = AssetEntity.builder();

        if (Objects.nonNull(dto.getAmount())) {
            entityBuilder.amount(dto.getAmount());
        }

        if (Objects.nonNull(dto.getId())) {
            entityBuilder.id(dto.getId());
        }

        if (Objects.nonNull(dto.getDescription())) {
            entityBuilder.description(dto.getDescription());
        }

        if (Objects.nonNull(dto.getIncomeDate())) {
            entityBuilder.incomeDate(dto.getIncomeDate());
        }

        if (Objects.nonNull(dto.getCategory())) {
            entityBuilder.category(dto.getCategory());
        }

        if (Objects.nonNull(user)) {
            entityBuilder.user(user);
        }

        return entityBuilder.build();

    }

    public AssetDto fromEntityToDto(AssetEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        var dtoBuilder = AssetDto.builder();

        if (Objects.nonNull(entity.getAmount())) {
            dtoBuilder.amount(entity.getAmount());
        }

        if (Objects.nonNull(entity.getId())) {
            dtoBuilder.id(entity.getId());
        }

        if (Objects.nonNull(entity.getIncomeDate())) {
            dtoBuilder.incomeDate(entity.getIncomeDate());
        }

        if (Objects.nonNull(entity.getDescription())) {
            dtoBuilder.description(entity.getDescription());
        }

        if (Objects.nonNull(entity.getCategory())) {
            dtoBuilder.category(entity.getCategory());
        }

        return dtoBuilder.build();

    }

}
