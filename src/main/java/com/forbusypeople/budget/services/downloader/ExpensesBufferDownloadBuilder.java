package com.forbusypeople.budget.services.downloader;

import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ExpensesBufferDownloadBuilder {

    public StringBuffer prepareBuffer(List<ExpensesDto> dtos,
                                      String separator) {
        StringBuffer stringBuffer = new StringBuffer(
                "Amount" + separator + "Category" + separator + "Date");
        dtos.forEach(asset ->
                             stringBuffer
                                     .append("\n")
                                     .append(asset.getAmount())
                                     .append(separator)
                                     .append(asset.getCategory())
                                     .append(separator)
                                     .append(asset.getPurchaseDate())
        );

        return stringBuffer;
    }

}
