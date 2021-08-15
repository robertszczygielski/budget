package com.forbusypeople.budget.services.downloader;

import com.forbusypeople.budget.configurations.DownloadConfiguration;
import com.forbusypeople.budget.enums.DownloadSpecificationEnum;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DownloadService {

    private final AssetsService assetsService;
    private final ExpensesService expensesService;

    private final ResponsePrepareService responsePrepareService;
    private final AssetsBufferDownloadBuilder assetsBufferDownloadBuilder;
    private final ExpensesBufferDownloadBuilder expensesBufferDownloadBuilder;
    private final DownloadConfiguration downloadConfiguration;

    public void getFileToDownload(HttpServletResponse response,
                                  DownloadSpecificationEnum downloadSpecificationEnum,
                                  Map<String, String> filter) {
        switch (downloadSpecificationEnum) {
            case ASSETS -> prepareResponseForAssets(response, filter);
            case EXPENSES -> prepareResponseForExpenses(response, filter);
        }
    }

    private void prepareResponseForAssets(HttpServletResponse response,
                                          Map<String, String> filter) {
        var assets = getAllAssets(filter);
        var assetsBuffer =
                assetsBufferDownloadBuilder.prepareBuffer(
                        assets,
                        downloadConfiguration.getSeparator()
                );

        responsePrepareService.addToResponse(
                response,
                assetsBuffer,
                downloadConfiguration.getAssetsFilename()
        );
    }

    private void prepareResponseForExpenses(HttpServletResponse response,
                                            Map<String, String> filter) {
        var expenses = getAllExpenses(filter);
        var expensesBuffer =
                expensesBufferDownloadBuilder.prepareBuffer(
                        expenses,
                        downloadConfiguration.getSeparator()
                );

        responsePrepareService.addToResponse(
                response,
                expensesBuffer,
                downloadConfiguration.getExpensesFilename()
        );
    }

    private List<ExpensesDto> getAllExpenses(Map<String, String> filter) {
        if (Objects.isNull(filter)) {
            return expensesService.getAllExpenses();
        }
        return expensesService.getFilteredExpenses(filter);
    }

    private List<AssetDto> getAllAssets(Map<String, String> filter) {
        if (Objects.isNull(filter)) {
            return assetsService.getAllAssets();
        }
        return assetsService.getAssetsByFilter(filter);
    }

}
