package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.AuthenticationService;
import com.forbusypeople.budget.services.dtos.AuthenticationJwtToken;
import com.forbusypeople.budget.services.dtos.AuthenticationUserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public AuthenticationJwtToken getAuthenticationToken(@RequestBody AuthenticationUserDto authenticationUserDto) {
        return authenticationService.createAuthenticationToken(authenticationUserDto);
    }

}
