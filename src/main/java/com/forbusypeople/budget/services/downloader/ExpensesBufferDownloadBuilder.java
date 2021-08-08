package com.forbusypeople.budget.services.downloader;

import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ExpensesBufferDownloadBuilder {

    private final String SEPARATOR = ";";

    public StringBuffer prepareBuffer(List<ExpensesDto> dtos) {
        StringBuffer stringBuffer = new StringBuffer(
                "Amount" + SEPARATOR + "Category" + SEPARATOR + "Date");
        dtos.forEach(asset ->
                             stringBuffer
                                     .append("\n")
                                     .append(asset.getAmount())
                                     .append(SEPARATOR)
                                     .append(asset.getCategory())
                                     .append(SEPARATOR)
                                     .append(asset.getPurchaseDate())
        );

        return stringBuffer;
    }

}
