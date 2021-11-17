package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.aspects.annotations.SetLoggedUser;
import com.forbusypeople.budget.repositories.entities.UserEntity;
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
    @SetLoggedUser
    private void uploadFile(
            UserEntity user,
            @RequestParam("file")
                    MultipartFile file) {
        uploadService.uploadFile(user, file);
    }
}
