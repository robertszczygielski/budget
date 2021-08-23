package com.forbusypeople.budget.services.uploader;

import com.forbusypeople.budget.services.AssetsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UploadService {

    private final AssetsService assetsService;

    private final ParseAssetsService parseAssetsService;

    public void uploadFile(MultipartFile file) {
        try {
            var inputCsv = file.getInputStream();
            var bufferedReader = new BufferedReader(
                    new InputStreamReader(inputCsv, "UTF-8")
            ).lines()
                    .collect(Collectors.toList());

            bufferedReader.remove(0);
            var dtos = parseAssetsService.mapToDto(bufferedReader);

            assetsService.setAsset(dtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
