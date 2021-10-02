package com.forbusypeople.budget.services;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.mappers.ExpensesEstimatePercentageMapper;
import com.forbusypeople.budget.repositories.ExpensesEstimatePercentageRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.users.UserLogInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpensesEstimatePercentageService {

    private final ExpensesEstimatePercentageRepository expensesEstimatePercentageRepository;
    private final UserLogInfoService userLogInfoService;
    private final ExpensesEstimatePercentageMapper expensesEstimatePercentageMapper;

    @Transactional
    public void saveEstimation(Map<ExpensesCategory, BigDecimal> estination) {
        var user = userLogInfoService.getLoggedUserEntity();
        var allEntities =
                expensesEstimatePercentageMapper.fromMapToEntity(estination, user);

        expensesEstimatePercentageRepository.saveAll(allEntities);
    }

    public Map<ExpensesCategory, BigDecimal> getEstimation() {
        var user = userLogInfoService.getLoggedUserEntity();
        return getEstimation(user);
    }

    public Map<ExpensesCategory, BigDecimal> getEstimation(UserEntity user) {
        var entitiesOptional = expensesEstimatePercentageRepository.findAllByUser(user);

        if (entitiesOptional.isPresent()) {
            var entities = entitiesOptional.get();
            return entities.stream()
                    .collect(Collectors.toMap(it -> it.getCategory(),
                                              it -> it.getPercentage()
                             )
                    );
        }

        return mapWithZeroValues();
    }

    private Map<ExpensesCategory, BigDecimal> mapWithZeroValues() {
        return new HashMap<>() {{
            put(ExpensesCategory.FUN, BigDecimal.ZERO);
            put(ExpensesCategory.FOR_LIFE, BigDecimal.ZERO);
            put(ExpensesCategory.OTHERS, BigDecimal.ZERO);
            put(ExpensesCategory.EDUCATION, BigDecimal.ZERO);
        }};
    }
}
