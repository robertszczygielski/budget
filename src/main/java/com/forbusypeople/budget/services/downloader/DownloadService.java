package com.forbusypeople.budget.services.downloader;

import com.forbusypeople.budget.configurations.DownloadConfiguration;
import com.forbusypeople.budget.enums.DownloadSpecificationEnum;
import com.forbusypeople.budget.repositories.entities.UserEntity;
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

    public void getFileToDownload(UserEntity user,
                                  HttpServletResponse response,
                                  DownloadSpecificationEnum downloadSpecificationEnum,
                                  Map<String, String> filter) {
        switch (downloadSpecificationEnum) {
            case ASSETS -> prepareResponseForAssets(user, response, filter);
            case EXPENSES -> prepareResponseForExpenses(user, response, filter);
        }
    }

    private void prepareResponseForAssets(UserEntity user,
                                          HttpServletResponse response,
                                          Map<String, String> filter) {
        var assets = getAllAssets(user, filter);
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

    private void prepareResponseForExpenses(UserEntity user,
                                            HttpServletResponse response,
                                            Map<String, String> filter) {
        var expenses = getAllExpenses(user, filter);
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

    private List<ExpensesDto> getAllExpenses(UserEntity user,
                                             Map<String, String> filter) {
        if (Objects.isNull(filter)) {
            return expensesService.getAllExpenses();
        }
        return expensesService.getFilteredExpenses(filter);
    }

    private List<AssetDto> getAllAssets(UserEntity user,
                                        Map<String, String> filter) {
        if (Objects.isNull(filter)) {
            return assetsService.getAllAssets(user);
        }
        return assetsService.getAssetsByFilter(filter);
    }

}
