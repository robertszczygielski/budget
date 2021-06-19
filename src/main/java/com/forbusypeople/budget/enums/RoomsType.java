package com.forbusypeople.budget.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoomsType {

    ROOM_XS("size xs"),
    ROOM_S("size s"),
    ROOM_M("size m"),
    ROOM_L("size l"),
    ROOM_XL("size xl"),
    ROOM_XXL("size xxl");

    public final String sizeName;

}
