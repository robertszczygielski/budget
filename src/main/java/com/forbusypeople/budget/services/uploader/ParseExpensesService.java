package com.forbusypeople.budget.services.uploader;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
class ParseExpensesService {

    public List<ExpensesDto> mapToDto(List<String> bufferedReader) {
        return bufferedReader.stream()
                .map(data -> data.split(";"))
                .map(array -> ExpensesDto.builder()
                        .amount(new BigDecimal(array[0]))
                        .category(ExpensesCategory.valueOf(array[1].toUpperCase()))
                        .purchaseDate(Instant.parse(array[2] + "T01:01:01.001Z"))
                        .description(array.length == 4 ? array[3] : null)
                        .build())
                .collect(Collectors.toList());
    }
}
