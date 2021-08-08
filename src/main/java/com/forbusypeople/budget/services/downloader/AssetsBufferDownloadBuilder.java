package com.forbusypeople.budget.services.downloader;

import com.forbusypeople.budget.services.dtos.AssetDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AssetsBufferDownloadBuilder {

    private final String SEPARATOR = ";";

    public StringBuffer prepareBuffer(List<AssetDto> dtos) {
        StringBuffer stringBuffer = new StringBuffer(
                "Amount" + SEPARATOR + "Category" + SEPARATOR + "Date" + SEPARATOR + "Description");
        dtos.forEach(asset ->
                             stringBuffer
                                     .append("\n")
                                     .append(asset.getAmount())
                                     .append(SEPARATOR)
                                     .append(asset.getCategory())
                                     .append(SEPARATOR)
                                     .append(asset.getIncomeDate())
                                     .append(SEPARATOR)
                                     .append(asset.getDescription())
        );

        return stringBuffer;
    }

}
