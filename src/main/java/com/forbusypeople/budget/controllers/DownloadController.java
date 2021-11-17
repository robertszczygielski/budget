package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.aspects.annotations.SetLoggedUser;
import com.forbusypeople.budget.enums.DownloadSpecificationEnum;
import com.forbusypeople.budget.repositories.entities.UserEntity;
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
    @SetLoggedUser
    public void getDownloadAssets(UserEntity user,
                                  HttpServletResponse response) {
        downloadService.getFileToDownload(
                user,
                response,
                DownloadSpecificationEnum.ASSETS,
                null
        );
    }

    @GetMapping("/expenses")
    @SetLoggedUser
    public void getDownloadExpenses(UserEntity user,
                                    HttpServletResponse response) {
        downloadService.getFileToDownload(
                user, response,
                DownloadSpecificationEnum.EXPENSES,
                null
        );
    }

    @GetMapping("/assets/filter")
    @SetLoggedUser
    public void getDownloadFilteredAssets(
            UserEntity user,
            HttpServletResponse response,
            @RequestParam Map<String, String> filter
    ) {
        downloadService.getFileToDownload(
                user, response,
                DownloadSpecificationEnum.ASSETS,
                filter
        );
    }

    @GetMapping("/expenses/filter")
    @SetLoggedUser
    public void getDownloadFilteredExpenses(
            UserEntity user,
            HttpServletResponse response,
            @RequestParam Map<String, String> filter
    ) {
        downloadService.getFileToDownload(
                user, response,
                DownloadSpecificationEnum.EXPENSES,
                filter
        );
    }
}
