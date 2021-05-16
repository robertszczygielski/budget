package com.forbusypeople.budget.filters;

import com.forbusypeople.budget.repositories.ExpensesRepository;
import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class ExpensesFilterRange extends FilterRangeAbstract {

    private final ExpensesRepository expensesRepository;

    public ExpensesFilterRange(ExpensesRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }

    @Override
    protected List<ExpensesEntity> getAllEntityBetweenDate(UserEntity user,
                                                           Instant fromDate,
                                                           Instant toDate) {
        return expensesRepository.findAllByBetweenDate(user, fromDate, toDate);
    }

    @Override
    protected String getFilterName() {
        return "ExpensesFilter";
    }
}
