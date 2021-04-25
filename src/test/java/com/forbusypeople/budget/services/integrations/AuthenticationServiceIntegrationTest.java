package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.AuthenticationMessageEnum;
import com.forbusypeople.budget.excetpions.BudgetInvalidUsernameOrPasswordException;
import com.forbusypeople.budget.services.AuthenticationService;
import com.forbusypeople.budget.services.dtos.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationServiceIntegrationTest extends InitIntegrationTestData {

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
        initDatabaseByPrimeUser();

        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername("incorrectUserName");
        dto.setPassword(USER_PASSWORD_PRIME);

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
        initDatabaseByPrimeUser();

        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(USER_NAME_PRIME);
        dto.setPassword("IncorrectPassword");

        // when
        var result = assertThrows(BudgetInvalidUsernameOrPasswordException.class,
                () -> authenticationService.createAuthenticationToken(dto));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());


    }

}
