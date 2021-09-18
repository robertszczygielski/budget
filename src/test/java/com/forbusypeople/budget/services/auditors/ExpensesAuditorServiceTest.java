package com.forbusypeople.budget.services.auditors;

import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.services.ExpensesEstimatePercentageService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpensesAuditorServiceTest {

    @Mock
    private ExpensesEstimatePercentageService expensesEstimatePercentageService;
    @Mock
    private ExpensesAuditorCalculator expensesAuditorCalculator;

    private ExpensesAuditorService expensesAuditorService;

    @BeforeEach
    public void setup() {
        expensesAuditorService = new ExpensesAuditorService(
                expensesEstimatePercentageService,
                expensesAuditorCalculator
        );
    }

    @Test
    void shouldReturnInformationAboutExceedExpenses() {
        // given
        var month = MonthsEnum.JANUARY;
        var year = "2020";
        when(expensesAuditorCalculator.getAssetsInMonth(month, year)).thenReturn(getAssetsList());
        when(expensesAuditorCalculator.getExpensesInMonth(month, year)).thenReturn(getExpensesList());

        // when
        var result = expensesAuditorService.getAudit(MonthsEnum.JANUARY, "2020");

        // then
        assertThat(result).isEqualTo(new BigDecimal("9"));

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