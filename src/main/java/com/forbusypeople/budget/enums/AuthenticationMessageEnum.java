package com.forbusypeople.budget.enums;

public enum AuthenticationMessageEnum {

    USER_NOT_FOUND("user not found"),
    USER_ALREADY_EXISTS("user already exists"),
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password");

    private final String message;

    AuthenticationMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
