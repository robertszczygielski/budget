package com.forbusypeople.budget.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DownloadSeparatorEnum {

    SEMICOLON(";"),
    COMMA(","),
    TAB("\t"),
    SPACES(" ");

    private final String sign;

}
