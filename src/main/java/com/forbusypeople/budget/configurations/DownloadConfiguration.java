package com.forbusypeople.budget.configurations;

import com.forbusypeople.budget.enums.DownloadSeparatorEnum;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "download.files")
@Setter
public class DownloadConfiguration {

    private String separator;
    private String expensesFilename;
    private String assetsFilename;

    public String getSeparator() {
        return separator != null
                ? DownloadSeparatorEnum.valueOf(separator.toUpperCase()).getSign()
                : DownloadSeparatorEnum.SEMICOLON.getSign();
    }

    public String getExpensesFilename() {
        return expensesFilename != null ? expensesFilename : "expensesFilename";
    }

    public String getAssetsFilename() {
        return assetsFilename != null ? assetsFilename : "assetsFilename";
    }

}
