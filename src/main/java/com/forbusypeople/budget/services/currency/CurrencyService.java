package com.forbusypeople.budget.services.currency;

import com.forbusypeople.budget.configurations.CurrencyConfiguration;
import com.forbusypeople.budget.feign.FeignNbpClient;
import com.forbusypeople.budget.feign.dto.FeignNbpDto;
import feign.Feign;
import feign.gson.GsonDecoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CurrencyService {

    private final String NBP_URL = "http://api.nbp.pl/api/exchangerates";

    private final CurrencyConfiguration currencyConfiguration;

    public List<String> getCurrencyCodes() {
        return currencyConfiguration.getCodes().stream().collect(Collectors.toList());
    }

    public FeignNbpDto getCurrencyFromNbp(String currencyCode) {
        var feignClient = getFeignNbpClient();
        return currencyCode.equals("USD") ? feignClient.getUsdCurrency() : feignClient.getEurCurrency();
    }

    private FeignNbpClient getFeignNbpClient() {
        return Feign.builder()
                .decoder(new GsonDecoder())
                .target(FeignNbpClient.class, NBP_URL);
    }

}
