package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {

    private final DownloadService downloadService;

    @GetMapping("/assets")
    public void getDownloadAssets(HttpServletResponse response) {
        downloadService.downloadAssets(response);
    }
}
