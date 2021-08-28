package com.forbusypeople.budget.services.uploader;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
class ParseAssetsService {

    public List<AssetDto> mapToDto(List<String> bufferedReader) {
        return bufferedReader.stream()
                .map(data -> data.split(";"))
                .map(array -> AssetDto.builder()
                        .amount(new BigDecimal(array[0]))
                        .category(AssetCategory.valueOf(array[1].toUpperCase()))
                        .incomeDate(Instant.parse(array[2] + "T01:01:01.001Z"))
                        .description(array[3])
                        .build())
                .collect(Collectors.toList());
    }
}
