package com.forbusypeople.budget.filters;

import com.forbusypeople.budget.enums.FilterParametersEnum;
import com.forbusypeople.budget.enums.FilterSpecification;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.validators.filter.FilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

abstract class FilterRangeAbstract<T> {

    @Autowired
    private FilterStrategy filterStrategy;

    private final static String DATE_SUFFIX = "T00:00:00.001Z";

    public List<T> getAllByFilter(UserEntity user,
                                  Map<String, String> filter,
                                  FilterSpecification specification) {

        filterStrategy.checkFilterForSpecification(filter, specification);

        if (isFilterForFromToDate(filter)) {
            var fromDate = filter.get(FilterParametersEnum.FROM_DATE.getKey());
            var toDate = filter.get(FilterParametersEnum.TO_DATE.getKey());

            return getAllEntityBetweenDate(user,
                                           parseDateToInstant(fromDate),
                                           parseDateToInstant(toDate),
                                           filter.get(FilterParametersEnum.CATEGORY.getKey())
            );
        } else if (isFilterForMonthYear(filter)) {
            MonthsEnum month = MonthsEnum.valueOf(
                    filter.get(FilterParametersEnum.MONTH.getKey()).toUpperCase());
            String year = filter.get(FilterParametersEnum.YEAR.getKey());
            return getAllExpensesForMonthInYear(user,
                                                month,
                                                year,
                                                filter.get(FilterParametersEnum.CATEGORY.getKey())
            );
        }

        return Collections.emptyList();
    }


    private boolean isFilterForMonthYear(Map<String, String> filter) {
        return filter.containsKey(FilterParametersEnum.YEAR.getKey())
                && filter.containsKey(FilterParametersEnum.MONTH.getKey());
    }

    private boolean isFilterForFromToDate(Map<String, String> filter) {
        return filter.containsKey(FilterParametersEnum.FROM_DATE.getKey())
                && filter.containsKey(FilterParametersEnum.TO_DATE.getKey());
    }

    private List<T> getAllExpensesForMonthInYear(UserEntity user,
                                                 MonthsEnum month,
                                                 String year,
                                                 String category) {
        String from = month.getFirstDayForYear(year);
        String to = month.getLastDayForYear(year);

        return getAllEntityBetweenDate(user,
                                       parseDateToInstant(from),
                                       parseDateToInstant(to),
                                       category
        );
    }

    private Instant parseDateToInstant(String date) {
        return Instant.parse(date + DATE_SUFFIX);
    }

    protected abstract List<T> getAllEntityBetweenDate(UserEntity user,
                                                       Instant fromDate,
                                                       Instant toDate,
                                                       String category);

}
