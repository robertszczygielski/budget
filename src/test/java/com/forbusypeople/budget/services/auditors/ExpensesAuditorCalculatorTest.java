package com.forbusypeople.budget.services.auditors;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesEstimatePercentageService;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpensesAuditorCalculatorTest {

    @Mock
    private ExpensesEstimatePercentageService expensesEstimatePercentageService;
    @Mock
    private AssetsService assetsService;
    @Mock
    private ExpensesService expensesService;


    private ExpensesAuditorCalculator expensesAuditorCalculator;

    @BeforeEach
    public void setup() {
        expensesAuditorCalculator = new ExpensesAuditorCalculator(
                expensesEstimatePercentageService,
                assetsService,
                expensesService
        );
    }
    
    @ParameterizedTest
    @CsvSource({
            "fun, 10, 100.0",
            "others, 20, 200.0",
            "for_life, 30, 300.0",
            "education, 40, 400.0",
            "education, 0, 0"
    })
    void shouldReturnPercentAmountForAssetsForExpensesCategory(String category,
                                                               String percent,
                                                               String expected) {
        // given
        var expensesCategory = ExpensesCategory.valueOf(category.toUpperCase());
        Map<ExpensesCategory, BigDecimal> estimations = new HashMap<>() {{
            put(expensesCategory, new BigDecimal(percent));
        }};
        when(expensesEstimatePercentageService.getEstimation()).thenReturn(estimations);

        // when
        var result = expensesAuditorCalculator.getPlanPercentAudit(
                expensesCategory,
                new BigDecimal("1000")
        );

        // then
        assertThat(result).isEqualTo(new BigDecimal(expected));

    }

    @ParameterizedTest
    @CsvSource({
            "fun, 100",
            "others, 120",
            "for_life, 180",
            "education, 200"
    })
    void shouldReturnPercentAmountForExpensesForExpensesCategory(String category,
                                                                 String expected) {
        // given
        List<ExpensesDto> dtos = asList(
                ExpensesDto.builder().category(ExpensesCategory.FUN).amount(new BigDecimal("90")).build(),
                ExpensesDto.builder().category(ExpensesCategory.FUN).amount(new BigDecimal("10")).build(),
                ExpensesDto.builder().category(ExpensesCategory.OTHERS).amount(new BigDecimal("60")).build(),
                ExpensesDto.builder().category(ExpensesCategory.OTHERS).amount(new BigDecimal("60")).build(),
                ExpensesDto.builder().category(ExpensesCategory.FOR_LIFE).amount(new BigDecimal("90")).build(),
                ExpensesDto.builder().category(ExpensesCategory.FOR_LIFE).amount(new BigDecimal("90")).build(),
                ExpensesDto.builder().category(ExpensesCategory.EDUCATION).amount(new BigDecimal("100")).build(),
                ExpensesDto.builder().category(ExpensesCategory.EDUCATION).amount(new BigDecimal("100")).build()
        );
        when(expensesService.getFilteredExpenses(anyMap())).thenReturn(dtos);

        // when
        var result = expensesAuditorCalculator.getRealPercentAudit(
                ExpensesCategory.valueOf(category.toUpperCase()),
                MonthsEnum.JANUARY,
                "2020"
        );

        // then
        assertThat(result).isEqualTo(new BigDecimal(expected));

    }

}