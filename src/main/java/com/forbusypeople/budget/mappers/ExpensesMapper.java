package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ExpensesMapper {
    public ExpensesEntity formDtoToEntity(ExpensesDto dto, UserEntity user) {
        var entity = new ExpensesEntity();

        if (Objects.nonNull(dto.getCategory())) {
            entity.setCategory(dto.getCategory());
        }
        if (Objects.nonNull(dto.getAmount())) {
            entity.setAmount(dto.getAmount());
        }
        if (Objects.nonNull(dto.getId())) {
            entity.setId(dto.getId());
        }
        if (Objects.nonNull(dto.getPurchaseDate())) {
            entity.setPurchaseDate(dto.getPurchaseDate());
        }
        if (Objects.nonNull(user)) {
            entity.setUser(user);
        }

        return entity;
    }

    public ExpensesDto formEntityToDto(ExpensesEntity entity) {
        var dto = new ExpensesDto();

        if (Objects.nonNull(entity.getCategory())) {
            dto.setCategory(entity.getCategory());
        }
        if (Objects.nonNull(entity.getAmount())) {
            dto.setAmount(entity.getAmount());
        }
        if (Objects.nonNull(entity.getId())) {
            dto.setId(entity.getId());
        }
        if (Objects.nonNull(entity.getPurchaseDate())) {
            dto.setPurchaseDate(entity.getPurchaseDate());
        }

        return dto;

    }

    public List<ExpensesDto> fromEntitiesToDtos(List<ExpensesEntity> allExpenses) {
        return allExpenses.stream()
                .map(this::formEntityToDto)
                .collect(Collectors.toList());
    }
}
