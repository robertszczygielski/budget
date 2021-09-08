package com.forbusypeople.budget.services.auditors;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesEstimatePercentageService;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpensesAuditorServiceTest {

    @Mock
    private AssetsService assetsService;
    @Mock
    private ExpensesService expensesService;
    @Mock
    private ExpensesEstimatePercentageService expensesEstimatePercentageService;

    private ExpensesAuditorService expensesAuditorService;

    @BeforeEach
    public void setup() {
        expensesAuditorService = new ExpensesAuditorService(
                assetsService,
                expensesService,
                expensesEstimatePercentageService
        );
    }

    @Test
    void shouldReturnInformationAboutExceedExpenses() {
        // given
        when(assetsService.getAssetsByFilter(anyMap())).thenReturn(getAssetsList());
        when(expensesService.getFilteredExpenses(anyMap())).thenReturn(getExpensesList());

        // when
        var result = expensesAuditorService.getAudit(MonthsEnum.JANUARY, "2020");

        // then
        assertThat(result).isEqualTo(new BigDecimal("9"));

    }

    @ParameterizedTest
    @CsvSource({
            "fun, 10, 100.0",
            "others, 20, 200.0",
            "for_life, 30, 300.0",
            "education, 40, 400.0"
    })
    void shouldReturnPercentForExpensesCategory(String category,
                                                String percent,
                                                String expected) {
        // given
        var expensesCategory = ExpensesCategory.valueOf(category.toUpperCase());
        Map<ExpensesCategory, BigDecimal> estimations = new HashMap<>() {{
            put(expensesCategory, new BigDecimal(percent));
        }};
        when(expensesEstimatePercentageService.getEstimation()).thenReturn(estimations);

        // when
        var result = expensesAuditorService.getPercentAudit(
                expensesCategory,
                new BigDecimal("1000")
        );

        // then
        assertThat(result).isEqualTo(new BigDecimal(expected));

    }

    private List<ExpensesDto> getExpensesList() {
        return asList(
                ExpensesDto.builder()
                        .amount(BigDecimal.ONE)
                        .build()
        );
    }

    private List<AssetDto> getAssetsList() {
        return asList(
                AssetDto.builder()
                        .amount(BigDecimal.TEN)
                        .build()
        );
    }
}