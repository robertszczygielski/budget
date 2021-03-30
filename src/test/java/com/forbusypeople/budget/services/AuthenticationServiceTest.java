package com.forbusypeople.budget.services;


import com.forbusypeople.budget.services.dtos.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsServiceImpl userDetailsService;

    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        var jwtService = new JWTService();
        authenticationService = new AuthenticationService(
                userDetailsService,
                jwtService,
                authenticationManager);
    }

    @Test
    void shouldReturnTokenWhenUserAndPasswordMatch() {
        // given
        String userName = "userName";
        String userPassword = "userPassword";
        String expectedTokenHeader = "eyJhbGciOiJIUzI1NiJ9";

        UserDetailsDto authenticationUser = new UserDetailsDto();
        authenticationUser.setUsername(userName);
        authenticationUser.setPassword(userPassword);

        Collection authorities = Collections.emptyList();
        UserDetails userDetails = new User(userName, userPassword , authorities);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userName, userPassword);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        when(userDetailsService.loadUserByUsername(userName)).thenReturn(userDetails);

        // when
        var result = authenticationService.createAuthenticationToken(authenticationUser);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getJwtToken()).isNotNull();
        assertThat(result.getJwtToken().substring(0, 20)).isEqualTo(expectedTokenHeader);

    }
}