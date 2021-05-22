package com.forbusypeople.budget.validators.filter;

import com.forbusypeople.budget.enums.FilterSpecification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FilterStrategy {

    private final Map<String, FilterParametersValidator> allValidators;

    public FilterStrategy(Map<String, FilterParametersValidator> allValidators) {
        this.allValidators = allValidators;
    }

    public void checkFilterForSpecification(Map<String, String> filter,
                                            FilterSpecification specification) {
        allValidators.get(specification.getForValidator())
                .assertFilter(filter);
    }
}
