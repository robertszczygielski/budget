package com.forbusypeople.budget.services;

import com.forbusypeople.budget.services.dtos.AuthenticationJwtToken;
import com.forbusypeople.budget.services.dtos.UserDetailsDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserDetailsServiceImpl userDetailsService,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationJwtToken createAuthenticationToken(UserDetailsDto userDetailsDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDetailsDto.getUsername(), userDetailsDto.getPassword()
        ));

        var userDetails = userDetailsService.loadUserByUsername(userDetailsDto.getUsername());
        var jwtToken = jwtService.generateJWTToken(userDetails);

        return new AuthenticationJwtToken(jwtToken);
    }
}
