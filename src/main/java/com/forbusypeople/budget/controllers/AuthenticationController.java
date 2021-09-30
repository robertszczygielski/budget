package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.AuthenticationService;
import com.forbusypeople.budget.services.dtos.AuthenticationJwtToken;
import com.forbusypeople.budget.services.dtos.UserDetailsDto;
import com.forbusypeople.budget.services.users.UserDetailsServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    UserDetailsServiceImpl userDetailsService) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public AuthenticationJwtToken getAuthenticationToken(@RequestBody UserDetailsDto userDetailsDto) {
        return authenticationService.createAuthenticationToken(userDetailsDto);
    }

    @PostMapping
    public UUID setUserDetails(@RequestBody UserDetailsDto userDetailsDto) {
        return userDetailsService.saveUser(userDetailsDto);
    }

    @DeleteMapping
    public void deleteUser() {
        userDetailsService.deleteUser();
    }

}
