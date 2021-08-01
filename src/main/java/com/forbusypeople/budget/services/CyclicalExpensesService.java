package com.forbusypeople.budget.services;

import com.forbusypeople.budget.mappers.CyclicalExpensesMapper;
import com.forbusypeople.budget.repositories.CyclicalExpensesRepository;
import com.forbusypeople.budget.repositories.entities.CyclicalExpensesEntity;
import com.forbusypeople.budget.services.dtos.CyclicalExpensesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CyclicalExpensesService {

    private final CyclicalExpensesRepository cyclicalExpensesRepository;
    private final CyclicalExpensesMapper cyclicalExpensesMapper;
    private final UserLogInfoService userLogInfoService;

    public List<UUID> saveCyclicalExpenses(List<CyclicalExpensesDto> dto) {
        var user = userLogInfoService.getLoggedUserEntity();
        var entity = cyclicalExpensesMapper.formDtoToEntityList(dto, user);
        var savedEntity = cyclicalExpensesRepository.saveAll(entity);

        return savedEntity.stream()
                .map(CyclicalExpensesEntity::getId)
                .collect(Collectors.toList());
    }

    public List<CyclicalExpensesDto> getAllCyclicalExpenses() {
        var user = userLogInfoService.getLoggedUserEntity();
        var entities = cyclicalExpensesRepository.findAllByUser(user);
        return cyclicalExpensesMapper.formEntityToDtoList(entities);
    }
}
