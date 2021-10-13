package com.forbusypeople.budget.services.uploader;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UploadServiceTest {

    @Mock
    private AssetsService assetsService;
    @Mock
    private ExpensesService expensesService;

    private final ParseAssetsService parseAssetsService = new ParseAssetsService();
    private final ParseExpensesService parseExpensesService = new ParseExpensesService();
    private UploadService uploadService;

    @BeforeEach
    public void setup() {
        uploadService = new UploadService(
                assetsService,
                expensesService,
                parseAssetsService,
                parseExpensesService
        );
    }

    @Test
    void shouldCorrectParseCsvToDtoListAndCallSetAssetFromService() throws IOException {
        // given
        var file = mock(MultipartFile.class);
        var stringCsv = "Amount;Category;Date;Description\n" +
                "200;Rent;2020-08-08;Narnia";
        var byteCsv = stringCsv.getBytes(StandardCharsets.UTF_8);
        var inputCsv = new ByteArrayInputStream(byteCsv);
        when(file.getInputStream()).thenReturn(inputCsv);

        var dtos = asList(
                AssetDto.builder()
                        .amount(new BigDecimal("200"))
                        .category(AssetCategory.RENT)
                        .incomeDate(Instant.parse("2020-08-08T01:01:01.001Z"))
                        .description("Narnia")
                        .build()
        );

        // when
        uploadService.uploadFile(file);

        // then
        Mockito.verify(assetsService, Mockito.times(1)).setAsset(dtos);
    }

    @ParameterizedTest
    @MethodSource
    void shouldCorrectParseCsvToDtoListAndCallSetExpensesFromService(ParameterTestData testData) throws IOException {
        // given
        var file = mock(MultipartFile.class);
        var byteCsv = testData.stringCsv.getBytes(StandardCharsets.UTF_8);
        var inputCsv = new ByteArrayInputStream(byteCsv);
        when(file.getInputStream()).thenReturn(inputCsv);

        var dtos = asList(testData.dto);

        // when
        uploadService.uploadFile(file);

        // then
        Mockito.verify(expensesService, Mockito.times(1)).setExpenses(dtos);
    }

    private static Stream<Arguments> shouldCorrectParseCsvToDtoListAndCallSetExpensesFromService() {
        return Stream.of(
                Arguments.of(new ParameterTestData(
                        """
                                amount;Category;Date
                                200;FUN;2020-08-08
                                """.trim(),
                        ExpensesDto.builder()
                                .amount(new BigDecimal("200"))
                                .category(ExpensesCategory.FUN)
                                .purchaseDate(Instant.parse("2020-08-08T01:01:01.001Z"))
                                .build()

                )),
                Arguments.of(new ParameterTestData(
                        """
                                Expenses
                                amount;Category;Date;Description
                                200;FUN;2020-08-08;Expenses description
                                """.trim(),
                        ExpensesDto.builder()
                                .amount(new BigDecimal("200"))
                                .category(ExpensesCategory.FUN)
                                .purchaseDate(Instant.parse("2020-08-08T01:01:01.001Z"))
                                .description("Expenses description")
                                .build()

                ))

        );
    }

    private static class ParameterTestData {
        public String stringCsv;
        public ExpensesDto dto;

        public ParameterTestData(String stringCsv,
                                 ExpensesDto dto) {
            this.stringCsv = stringCsv;
            this.dto = dto;
        }
    }
}