package com.forbusypeople.budget.filters;

import com.forbusypeople.budget.enums.FilterSpecification;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FilterRangeStrategy<T> {

    private final Map<String, FilterRangeAbstract> allFilterRange;

    public FilterRangeStrategy(Map<String, FilterRangeAbstract> allFilterRange) {
        this.allFilterRange = allFilterRange;
    }

    public List<T> getFilteredDataForSpecification(UserEntity user,
                                                   Map<String, String> filters,
                                                   FilterSpecification specification) {
        return allFilterRange.get(specification.getForRange())
                .getAllByFilter(user, filters, specification);
    }
}
