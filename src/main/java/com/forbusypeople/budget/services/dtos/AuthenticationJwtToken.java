package com.forbusypeople.budget.services.dtos;

public class AuthenticationJwtToken {
    private final String jwtToken;

    public AuthenticationJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
