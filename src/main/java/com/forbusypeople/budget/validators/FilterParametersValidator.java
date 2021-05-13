package com.forbusypeople.budget.validators;

import com.forbusypeople.budget.enums.FilterParametersCalendarEnum;

import java.util.Map;

public abstract class FilterParametersValidator {

    public void assertFilter(Map<String, String> filter) {
        checkIfFromDateExistToDateMissing(filter, "CKO8MP2A1000LYW42KL2Q6UD3");
        checkIfToDateExistFromDateMissing(filter, "CKO8MPVEI000MYW42IEGWOIBK");
        checkIfMonthExistYearMissing(filter, "CKO8MSABP000NYW423ZEENTG4");
        checkIfYearExistMonthMissing(filter, "CKO8MSABP000NYW423ZEENTG4");
    }

    private void checkIfYearExistMonthMissing(Map<String, String> filter,
                                              String errorCode) {
        if (filter.containsKey(FilterParametersCalendarEnum.YEAR.getKey())
                && !filter.containsKey(FilterParametersCalendarEnum.MONTH.getKey())) {

            throwException(FilterParametersCalendarEnum.MONTH.getKey(), errorCode);
        }
    }

    private void checkIfMonthExistYearMissing(Map<String, String> filter,
                                              String errorCode) {
        if (filter.containsKey(FilterParametersCalendarEnum.MONTH.getKey())
                && !filter.containsKey(FilterParametersCalendarEnum.YEAR.getKey())) {

            throwException(FilterParametersCalendarEnum.YEAR.getKey(), errorCode);
        }

    }

    private void checkIfToDateExistFromDateMissing(Map<String, String> filter,
                                                   String errorCode) {
        if (filter.containsKey(FilterParametersCalendarEnum.TO_DATE.getKey())
                && !filter.containsKey(FilterParametersCalendarEnum.FROM_DATE.getKey())) {

            throwException(FilterParametersCalendarEnum.FROM_DATE.getKey(), errorCode);
        }
    }

    private void checkIfFromDateExistToDateMissing(Map<String, String> filter,
                                                   String errorCode) {
        if (filter.containsKey(FilterParametersCalendarEnum.FROM_DATE.getKey())
                && !filter.containsKey(FilterParametersCalendarEnum.TO_DATE.getKey())) {

            throwException(FilterParametersCalendarEnum.TO_DATE.getKey(), errorCode);
        }
    }

    public abstract void throwException(String missingKey,
                                        String errorCode);
}
