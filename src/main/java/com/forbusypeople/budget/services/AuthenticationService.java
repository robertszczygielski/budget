package com.forbusypeople.budget.services;

import com.forbusypeople.budget.services.dtos.AuthenticationJwtToken;
import com.forbusypeople.budget.services.dtos.AuthenticationUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;

    public AuthenticationService(UserDetailsServiceImpl userDetailsService, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public AuthenticationJwtToken createAuthenticationToken(AuthenticationUserDto authenticationUserDto) {

        var userDetails = userDetailsService.loadUserByUsername(authenticationUserDto.getUsername());
        var jwtToken = jwtService.generateJWTToken(userDetails);

        return new AuthenticationJwtToken(jwtToken);
    }
}
