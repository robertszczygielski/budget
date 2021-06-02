package com.forbusypeople.budget.validators.filter;

import com.forbusypeople.budget.enums.FilterParametersEnum;

import java.util.Map;

abstract class FilterParametersValidator {

    public void assertFilter(Map<String, String> filter) {
        checkIfFromDateExistToDateMissing(filter, "CKO8MP2A1000LYW42KL2Q6UD3");
        checkIfToDateExistFromDateMissing(filter, "CKO8MPVEI000MYW42IEGWOIBK");
        checkIfMonthExistYearMissing(filter, "CKO8MSABP000NYW423ZEENTG4");
        checkIfYearExistMonthMissing(filter, "CKO8MSABP000NYW423ZEENTG4");
    }

    private void checkIfYearExistMonthMissing(Map<String, String> filter,
                                              String errorCode) {
        if (filter.containsKey(FilterParametersEnum.YEAR.getKey())
                && !filter.containsKey(FilterParametersEnum.MONTH.getKey())) {

            throwException(FilterParametersEnum.MONTH.getKey(), errorCode);
        }
    }

    private void checkIfMonthExistYearMissing(Map<String, String> filter,
                                              String errorCode) {
        if (filter.containsKey(FilterParametersEnum.MONTH.getKey())
                && !filter.containsKey(FilterParametersEnum.YEAR.getKey())) {

            throwException(FilterParametersEnum.YEAR.getKey(), errorCode);
        }

    }

    private void checkIfToDateExistFromDateMissing(Map<String, String> filter,
                                                   String errorCode) {
        if (filter.containsKey(FilterParametersEnum.TO_DATE.getKey())
                && !filter.containsKey(FilterParametersEnum.FROM_DATE.getKey())) {

            throwException(FilterParametersEnum.FROM_DATE.getKey(), errorCode);
        }
    }

    private void checkIfFromDateExistToDateMissing(Map<String, String> filter,
                                                   String errorCode) {
        if (filter.containsKey(FilterParametersEnum.FROM_DATE.getKey())
                && !filter.containsKey(FilterParametersEnum.TO_DATE.getKey())) {

            throwException(FilterParametersEnum.TO_DATE.getKey(), errorCode);
        }
    }

    public abstract void throwException(String missingKey,
                                        String errorCode);
}
