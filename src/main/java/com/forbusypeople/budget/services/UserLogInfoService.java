package com.forbusypeople.budget.services;

import com.forbusypeople.budget.repositories.UserRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserLogInfoService {

    private final UserRepository userRepository;

    public UserLogInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getLoggedUserEntity() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getPrincipal();

        var user = new UserEntity();
        user.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));

        return user;

//        return userRepository.findByUsername(username)
//                .orElseThrow(BudgetUserNotFoundException::new);
    }
}
