package com.forbusypeople.budget.feign;

import com.forbusypeople.budget.feign.dto.FeignNbpDto;
import feign.Headers;
import feign.RequestLine;

public interface FeignNbpClient {

    @RequestLine("GET /rates/A/usd")
    @Headers("Accept: application/json")
    FeignNbpDto getUsdCurrency();

    @RequestLine("GET /rates/A/eur")
    @Headers("Accept: application/json")
    FeignNbpDto getEurCurrency();

}
