package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.feign.dto.FeignNbpDto;
import com.forbusypeople.budget.services.currency.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/codes")
    public List<String> getCurrencyCodes() {
        return currencyService.getCurrencyCodes();
    }

    @GetMapping("/amount/{currencyCode}")
    public FeignNbpDto getCurrencyFor(@PathVariable String currencyCode) {
        return currencyService.getCurrencyFromNbp(currencyCode);
    }
}
