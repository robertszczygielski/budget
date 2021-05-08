package com.forbusypeople.budget.services;

import com.forbusypeople.budget.enums.FilterExpensesParametersEnum;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.mappers.ExpensesMapper;
import com.forbusypeople.budget.repositories.ExpensesRepository;
import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import com.forbusypeople.budget.validators.FilterParametersValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExpensesService {

    private final ExpensesRepository expensesRepository;
    private final ExpensesMapper expensesMapper;
    private final UserLogInfoService userLogInfoService;
    private final FilterParametersValidator parametersValidator;

    public ExpensesService(ExpensesRepository expensesRepository,
                           ExpensesMapper expensesMapper,
                           UserLogInfoService userLogInfoService,
                           FilterParametersValidator parametersValidator) {
        this.expensesRepository = expensesRepository;
        this.expensesMapper = expensesMapper;
        this.userLogInfoService = userLogInfoService;
        this.parametersValidator = parametersValidator;
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
        parametersValidator.assertFilter(filter);
        
        if (isFilterForFromToDate(filter)) {
            return getAllExpensesBetweenDate(
                    filter.get(FilterExpensesParametersEnum.FROM_DATE.getKey()),
                    filter.get(FilterExpensesParametersEnum.TO_DATE.getKey())
            );
        } else if (isFilterForMonthYear(filter)) {
            MonthsEnum month = MonthsEnum.valueOf(filter.get(FilterExpensesParametersEnum.MONTH.getKey()).toUpperCase());
            String year = filter.get(FilterExpensesParametersEnum.YEAR.getKey());
            return getAllExpensesForMonthInYear(month, year);
        }

        return Collections.emptyList();
    }

    private boolean isFilterForMonthYear(Map<String, String> filter) {
        return filter.containsKey(FilterExpensesParametersEnum.YEAR.getKey())
                && filter.containsKey(FilterExpensesParametersEnum.MONTH.getKey());
    }

    private boolean isFilterForFromToDate(Map<String, String> filter) {
        return filter.containsKey(FilterExpensesParametersEnum.FROM_DATE.getKey())
                && filter.containsKey(FilterExpensesParametersEnum.TO_DATE.getKey());
    }

    private List<ExpensesDto> getAllExpensesForMonthInYear(MonthsEnum month, String year) {
        String from = month.getFirstDayForYear(year);
        String to = month.getLastDayForYear(year);

        return getAllExpensesBetweenDate(from, to);
    }

    private List<ExpensesDto> getAllExpensesBetweenDate(String fromDate, String toDate) {
        var user = userLogInfoService.getLoggedUserEntity();
        var dateSuffix = "T00:00:00.001Z";
        var fromInstantDate = Instant.parse(fromDate + dateSuffix);
        var toInstantDate = Instant.parse(toDate + dateSuffix);

        return expensesRepository.findAllByBetweenDate(user, fromInstantDate, toInstantDate)
                .stream()
                .map(expensesMapper::formEntityToDto)
                .collect(Collectors.toList());
    }

    private void updateExpenses(ExpensesEntity entity, ExpensesDto dto) {
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
