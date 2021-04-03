package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.AuthenticationMessageEnum;
import com.forbusypeople.budget.excetpions.BudgetInvalidUsernameOrPasswordException;
import com.forbusypeople.budget.services.AuthenticationService;
import com.forbusypeople.budget.services.JWTService;
import com.forbusypeople.budget.services.UserDetailsServiceImpl;
import com.forbusypeople.budget.services.dtos.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AuthenticationServiceIntegrationTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        authenticationService = new AuthenticationService(
                userDetailsService,
                jwtService,
                authenticationManager
        );
    }

    @Test
    void shouldThrowAnBudgetInvalidUsernameOrPasswordExceptionWhenUsernameIsIncorrect() {
        // given
        initUserInDatabase();

        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername("incorrectUserName");
        dto.setPassword("user123");

        // when
        var result = assertThrows(BudgetInvalidUsernameOrPasswordException.class,
                () -> authenticationService.createAuthenticationToken(dto));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());


    }

    @Test
    void shouldThrowAnBudgetInvalidUsernameOrPasswordExceptionWhenPasswordIsIncorrect() {
        // given
        initUserInDatabase();

        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername("user123");
        dto.setPassword("IncorrectPassword");

        // when
        var result = assertThrows(BudgetInvalidUsernameOrPasswordException.class,
                () -> authenticationService.createAuthenticationToken(dto));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());


    }

    private void initUserInDatabase() {
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername("user123");
        dto.setPassword("user123");

        userDetailsService.saveUser(dto);
    }
}
