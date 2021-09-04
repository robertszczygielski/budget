package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.repositories.entities.ExpensesEstimatedPercentageEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExpensesEstimatePercentageMapper {

    default List<ExpensesEstimatedPercentageEntity> fromMapToEntity(
            Map<ExpensesCategory, BigDecimal> categoryBigDecimalMap,
            UserEntity user
    ) {
        return categoryBigDecimalMap.entrySet().stream()
                .map(it ->
                             ExpensesEstimatedPercentageEntity.builder()
                                     .percentage(it.getValue())
                                     .category(it.getKey())
                                     .user(user)
                                     .build())
                .collect(Collectors.toList());
    }

}
