package com.forbusypeople.budget.services;

import com.forbusypeople.budget.filters.ExpensesFilterRange;
import com.forbusypeople.budget.filters.FilterRangeAbstract;
import com.forbusypeople.budget.mappers.ExpensesMapper;
import com.forbusypeople.budget.repositories.ExpensesRepository;
import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExpensesService {

    private final ExpensesRepository expensesRepository;
    private final ExpensesMapper expensesMapper;
    private final UserLogInfoService userLogInfoService;
    private final FilterRangeAbstract<ExpensesEntity> filterRange;

    public ExpensesService(ExpensesRepository expensesRepository,
                           ExpensesMapper expensesMapper,
                           UserLogInfoService userLogInfoService,
                           ExpensesFilterRange filterRange) {
        this.expensesRepository = expensesRepository;
        this.expensesMapper = expensesMapper;
        this.userLogInfoService = userLogInfoService;
        this.filterRange = filterRange;
    }

    public void setExpenses(ExpensesDto dto) {
        var user = userLogInfoService.getLoggedUserEntity();
        var entity = expensesMapper.formDtoToEntity(dto, user);
        expensesRepository.save(entity);
    }

    public void deleteExpenses(ExpensesDto dto) {
        UserEntity user = userLogInfoService.getLoggedUserEntity();
        var entity = expensesMapper.formDtoToEntity(dto, user);
        expensesRepository.delete(entity);
    }

    @Transactional
    public void updateExpenses(ExpensesDto dto) {
        var entity = expensesRepository.findById(dto.getId());
        if (entity.isPresent()) {
            updateExpenses(entity.get(), dto);
        }
    }

    public List<ExpensesDto> getAllExpenses() {
        var user = userLogInfoService.getLoggedUserEntity();
        var allExpenses = expensesRepository.findAllByUser(user);
        return expensesMapper.fromEntitiesToDtos(allExpenses);
    }

    public List<ExpensesDto> getFilteredExpenses(Map<String, String> filter) {
        var user = userLogInfoService.getLoggedUserEntity();

        return filterRange.getAllByFilter(user, filter)
                .stream()
                .map(expensesMapper::formEntityToDto)
                .collect(Collectors.toList());
    }

    private void updateExpenses(ExpensesEntity entity,
                                ExpensesDto dto) {
        if (Objects.nonNull(dto.getPurchaseDate())
                && !dto.getPurchaseDate().equals(entity.getPurchaseDate())) {
            entity.setPurchaseDate(dto.getPurchaseDate());
        }
        if (Objects.nonNull(dto.getAmount())
                && !dto.getAmount().equals(entity.getAmount())) {
            entity.setAmount(dto.getAmount());
        }
        if (Objects.nonNull(dto.getCategory())
                && !dto.getCategory().equals(entity.getCategory())) {
            entity.setCategory(dto.getCategory());
        }
    }
}
