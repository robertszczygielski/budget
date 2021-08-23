package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.uploader.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping
    private void uploadFile(@RequestParam("file")
                                    MultipartFile file) {
        uploadService.uploadFile(file);
    }
}
