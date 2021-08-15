package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.enums.DownloadSpecificationEnum;
import com.forbusypeople.budget.services.downloader.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {

    private final DownloadService downloadService;

    @GetMapping("/assets")
    public void getDownloadAssets(HttpServletResponse response) {
        downloadService.getFileToDownload(
                response,
                DownloadSpecificationEnum.ASSETS,
                null
        );
    }

    @GetMapping("/expenses")
    public void getDownloadExpenses(HttpServletResponse response) {
        downloadService.getFileToDownload(
                response,
                DownloadSpecificationEnum.EXPENSES,
                null
        );
    }

    @GetMapping("/assets/filter")
    public void getDownloadFilteredAssets(
            HttpServletResponse response,
            @RequestParam Map<String, String> filter
    ) {
        downloadService.getFileToDownload(
                response,
                DownloadSpecificationEnum.ASSETS,
                filter
        );
    }

    @GetMapping("/expenses/filter")
    public void getDownloadFilteredExpenses(
            HttpServletResponse response,
            @RequestParam Map<String, String> filter
    ) {
        downloadService.getFileToDownload(
                response,
                DownloadSpecificationEnum.EXPENSES,
                filter
        );
    }
}
