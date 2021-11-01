package com.forbusypeople.budget.services.uploader;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;
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

    @ParameterizedTest
    @MethodSource
    void shouldCorrectParseCsvToDtoListAndCallSetAssetFromService(ParameterAssetsTestData testData) throws IOException {
        // given
        var file = mock(MultipartFile.class);
        var byteCsv = testData.stringCsv.getBytes(StandardCharsets.UTF_8);
        var inputCsv = new ByteArrayInputStream(byteCsv);
        when(file.getInputStream()).thenReturn(inputCsv);

        var dtos = List.of(testData.dto);

        // when
        uploadService.uploadFile(file);

        // then
        Mockito.verify(assetsService, Mockito.times(1)).setAsset(dtos);
    }

    @ParameterizedTest
    @MethodSource
    void shouldCorrectParseCsvToDtoListAndCallSetExpensesFromService(ParameterExpensesTestData testData) throws IOException {
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

    private static Stream<Arguments> shouldCorrectParseCsvToDtoListAndCallSetAssetFromService() {
        return Stream.of(
                Arguments.of(new ParameterAssetsTestData(
                        """
                                Amount;Category;Date;Description
                                200;Rent;2020-08-08;Narnia
                                """.trim(),
                        AssetDto.builder()
                                .amount(new BigDecimal("200"))
                                .incomeDate(Instant.parse("2020-08-08T01:01:01.001Z"))
                                .description("Narnia")
                                .category(AssetCategory.RENT)
                                .build()
                )),
                Arguments.of(new ParameterAssetsTestData(
                        """
                                assets
                                Amount;Currency;Category;Date;Description
                                200;USD;Rent;2020-08-08;Narnia
                                """.trim(),
                        AssetDto.builder()
                                .amount(new BigDecimal("200"))
                                .currencyCode("USD")
                                .incomeDate(Instant.parse("2020-08-08T01:01:01.001Z"))
                                .description("Narnia")
                                .category(AssetCategory.RENT)
                                .build()
                )),
                Arguments.of(new ParameterAssetsTestData(
                        """
                                Assets
                                Amount;Currency;Category;Date;Description
                                200;USD;Rent;2020-08-08;Narnia
                                """.trim(),
                        AssetDto.builder()
                                .amount(new BigDecimal("200"))
                                .currencyCode("USD")
                                .incomeDate(Instant.parse("2020-08-08T01:01:01.001Z"))
                                .description("Narnia")
                                .category(AssetCategory.RENT)
                                .build()
                ))
        );
    }

    private static Stream<Arguments> shouldCorrectParseCsvToDtoListAndCallSetExpensesFromService() {
        return Stream.of(
                Arguments.of(new ParameterExpensesTestData(
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
                Arguments.of(new ParameterExpensesTestData(
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

    private static class ParameterExpensesTestData {
        public String stringCsv;
        public ExpensesDto dto;

        public ParameterExpensesTestData(String stringCsv,
                                         ExpensesDto dto) {
            this.stringCsv = stringCsv;
            this.dto = dto;
        }
    }

    private static class ParameterAssetsTestData {
        public String stringCsv;
        public AssetDto dto;

        public ParameterAssetsTestData(String stringCsv,
                                       AssetDto dto) {
            this.stringCsv = stringCsv;
            this.dto = dto;
        }
    }
}