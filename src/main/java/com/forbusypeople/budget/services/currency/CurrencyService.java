package com.forbusypeople.budget.services.currency;

import com.forbusypeople.budget.configurations.CurrencyConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CurrencyService {

    private final CurrencyConfiguration currencyConfiguration;

    public List<String> getCurrencyCodes() {
        return currencyConfiguration.getCodes().stream().collect(Collectors.toList());
    }

}
