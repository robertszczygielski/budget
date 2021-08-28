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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

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

    @Test
    void shouldCorrectParseCsvToDtoListAndCallSetExpensesFromService() throws IOException {
        // given
        var file = mock(MultipartFile.class);
        var stringCsv = "Amount;Category;Date\n" +
                "200;FUN;2020-08-08";
        var byteCsv = stringCsv.getBytes(StandardCharsets.UTF_8);
        var inputCsv = new ByteArrayInputStream(byteCsv);
        when(file.getInputStream()).thenReturn(inputCsv);

        var dtos = asList(
                ExpensesDto.builder()
                        .amount(new BigDecimal("200"))
                        .category(ExpensesCategory.FUN)
                        .purchaseDate(Instant.parse("2020-08-08T01:01:01.001Z"))
                        .build()
        );

        // when
        uploadService.uploadFile(file);

        // then
        Mockito.verify(expensesService, Mockito.times(1)).setExpenses(dtos);
    }
}