package com.forbusypeople.budget.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties(prefix = "currencies")
@Data
public class CurrencyConfiguration {

    private Set<String> codes;

}
