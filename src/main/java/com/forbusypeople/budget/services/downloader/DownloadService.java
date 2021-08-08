package com.forbusypeople.budget.services.downloader;

import com.forbusypeople.budget.enums.DownloadSpecificationEnum;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class DownloadService {

    private final AssetsService assetsService;
    private final ExpensesService expensesService;

    private final ResponsePrepareService responsePrepareService;
    private final AssetsBufferDownloadBuilder assetsBufferDownloadBuilder;
    private final ExpensesBufferDownloadBuilder expensesBufferDownloadBuilder;

    public void getFileToDownload(HttpServletResponse response,
                                  DownloadSpecificationEnum downloadSpecificationEnum) {
        switch (downloadSpecificationEnum) {
            case ASSETS -> prepareResponseForAssets(response);
            case EXPENSES -> prepareResponseForExpenses(response);
        }
    }

    private void prepareResponseForAssets(HttpServletResponse response) {
        var assets = assetsService.getAllAssets();
        var assetsBuffer = assetsBufferDownloadBuilder.prepareBuffer(assets);

        responsePrepareService.addToResponse(response, assetsBuffer);
    }

    private void prepareResponseForExpenses(HttpServletResponse response) {
        var expenses = expensesService.getAllExpenses();
        var expensesBuffer = expensesBufferDownloadBuilder.prepareBuffer(expenses);

        responsePrepareService.addToResponse(response, expensesBuffer);
    }

}
