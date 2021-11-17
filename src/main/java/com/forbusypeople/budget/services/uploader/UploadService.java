package com.forbusypeople.budget.services.uploader;

import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UploadService {

    private final String ASSETS = "assets";

    private final AssetsService assetsService;
    private final ExpensesService expensesService;

    private final ParseAssetsService parseAssetsService;
    private final ParseExpensesService parseExpensesService;

    public void uploadFile(UserEntity user,
                           MultipartFile file) {
        try {
            var inputCsv = file.getInputStream();
            var bufferedReader = new BufferedReader(
                    new InputStreamReader(inputCsv, "UTF-8")
            ).lines()
                    .collect(Collectors.toList());

            if (isAsset(bufferedReader)) {
                saveAssets(user, bufferedReader);
            } else {
                saveExpenses(bufferedReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isAsset(List<String> bufferedReader) {
        var removedString = bufferedReader.remove(0);
        var numbersOfColumns = removedString.split(";");
        if (numbersOfColumns.length == 1) {
            bufferedReader.remove(0);
            return numbersOfColumns[0].toLowerCase(Locale.ROOT).equals(ASSETS);
        }
        var numbersOfColumnsForAssets = 4;
        return numbersOfColumns.length == numbersOfColumnsForAssets;
    }

    private void saveAssets(UserEntity user,
                            List<String> bufferedReader) {
        var dtos = parseAssetsService.mapToDto(bufferedReader);
        assetsService.setAsset(user, dtos);
    }

    private void saveExpenses(List<String> bufferedReader) {
        var dtos = parseExpensesService.mapToDto(bufferedReader);
        expensesService.setExpenses(dtos);
    }
}
