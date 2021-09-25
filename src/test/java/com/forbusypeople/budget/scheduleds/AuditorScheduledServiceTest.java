package com.forbusypeople.budget.scheduleds;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.services.auditors.ExpensesAuditorService;
import com.forbusypeople.budget.services.dtos.AuditDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditorScheduledServiceTest {

    @Mock
    private ExpensesAuditorService expensesAuditorService;

    private AuditorScheduledService auditorScheduledService;

    @BeforeEach
    public void setUp() {
        auditorScheduledService = new AuditorScheduledService(expensesAuditorService);
    }

    @Test
    void shouldReturnTrueIfTheAmountOfExpensesIsHigherThenExpected() {
        // given
        Map<ExpensesCategory, AuditDto> values = prepareValuesMapForSendMail();
        when(expensesAuditorService.getAuditForEstimate(any(), any())).thenReturn(values);

        // when
        boolean result = auditorScheduledService.mailShouldBeSend();

        // then
        assertThat(result).isTrue();

    }

    @Test
    void shouldReturnFalseIfTheAmountOfExpensesIsNotHigherThenExpected() {
        // given
        Map<ExpensesCategory, AuditDto> values = prepareValuesMapForNotSendMail();
        when(expensesAuditorService.getAuditForEstimate(any(), any())).thenReturn(values);

        // when
        boolean result = auditorScheduledService.mailShouldBeSend();

        // then
        assertThat(result).isFalse();

    }

    private Map<ExpensesCategory, AuditDto> prepareValuesMapForNotSendMail() {
        var values = new HashMap<ExpensesCategory, AuditDto>();

        values.put(
                ExpensesCategory.FUN,
                AuditDto.builder()
                        .expectedAmount(new BigDecimal(10))
                        .currentAmount(new BigDecimal(10))
                        .build()
        );
        values.put(
                ExpensesCategory.OTHERS,
                AuditDto.builder()
                        .expectedAmount(new BigDecimal(10))
                        .currentAmount(new BigDecimal(10))
                        .build()
        );
        values.put(
                ExpensesCategory.FOR_LIFE,
                AuditDto.builder()
                        .expectedAmount(new BigDecimal(10))
                        .currentAmount(new BigDecimal(10))
                        .build()
        );
        values.put(
                ExpensesCategory.EDUCATION,
                AuditDto.builder()
                        .expectedAmount(new BigDecimal(10))
                        .currentAmount(new BigDecimal(10))
                        .build()
        );

        return values;
    }

    private Map<ExpensesCategory, AuditDto> prepareValuesMapForSendMail() {
        var values = new HashMap<ExpensesCategory, AuditDto>();

        values.put(
                ExpensesCategory.FUN,
                AuditDto.builder()
                        .expectedAmount(new BigDecimal(10))
                        .currentAmount(new BigDecimal(20))
                        .build()
        );
        values.put(
                ExpensesCategory.OTHERS,
                AuditDto.builder()
                        .expectedAmount(new BigDecimal(10))
                        .currentAmount(new BigDecimal(10))
                        .build()
        );
        values.put(
                ExpensesCategory.FOR_LIFE,
                AuditDto.builder()
                        .expectedAmount(new BigDecimal(10))
                        .currentAmount(new BigDecimal(10))
                        .build()
        );
        values.put(
                ExpensesCategory.EDUCATION,
                AuditDto.builder()
                        .expectedAmount(new BigDecimal(10))
                        .currentAmount(new BigDecimal(10))
                        .build()
        );

        return values;
    }
}