package com.forbusypeople.budget.services.integrations;

import com.forbusypeople.budget.enums.AuthenticationMessageEnum;
import com.forbusypeople.budget.excetpions.BudgetUserAlreadyExistsInDatabaseException;
import com.forbusypeople.budget.excetpions.BudgetUserNotFoundException;
import com.forbusypeople.budget.repositories.UserRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.UserDetailsServiceImpl;
import com.forbusypeople.budget.services.dtos.UserDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserDetailsServiceImplIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private static final String USER_NAME = "userName";
    private static final String USER_PASSWORD = "userPassword";

    @Test
    void shouldReturnUserWithUserNameAndPasswordFromDatabase() {
        // given
        initDatabase();

        // when
        var result = userDetailsService.loadUserByUsername(USER_NAME);

        // then
        assertThat(result.getUsername()).isEqualTo(USER_NAME);
        assertThat(result.getPassword()).isEqualTo(USER_PASSWORD);

    }

    @Test
    void shouldSaveUserInToDatabase() {
        // given
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(USER_NAME);
        dto.setPassword(USER_PASSWORD);
        var bCryptPrefix = "$2a$10$";
        var bCryptRegex = "^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$";

        // when
        var userId = userDetailsService.saveUser(dto);

        // then
        assertThat(userId).isNotNull();
        var userEntityOptional = userRepository.findById(userId);
        var userEntity = userEntityOptional.get();
        assertAll(
                () -> assertThat(userEntity.getUsername()).isEqualTo(USER_NAME),
                () -> assertThat(userEntity.getPassword()).contains(bCryptPrefix),
                () -> assertThat(userEntity.getPassword()).matches(Pattern.compile(bCryptRegex))
        );
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFoundInDatabase() {
        // given
        initDatabase();

        // when
        var result = assertThrows(BudgetUserNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("fakeUser"));

        // then
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.USER_NOT_FOUND.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExistsInDatabase() {
        // given
        initDatabase();
        UserDetailsDto dto = new UserDetailsDto();
        dto.setPassword(USER_PASSWORD);
        dto.setUsername(USER_NAME);

        // when
        var result = assertThrows(BudgetUserAlreadyExistsInDatabaseException.class,
                () -> userDetailsService.saveUser(dto));

        // then
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.USER_ALREADY_EXISTS.getMessage());

    }

    private void initDatabase() {
        UserEntity entity = new UserEntity();
        entity.setPassword(USER_PASSWORD);
        entity.setUsername(USER_NAME);

        userRepository.save(entity);
    }
}