package com.forbusypeople.budget.services.users;

import com.forbusypeople.budget.excetpions.BudgetUserNotFoundException;
import com.forbusypeople.budget.repositories.UserRepository;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserLogInfoService {

    private final UserRepository userRepository;

    public UserLogInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getLoggedUserEntity() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = ((User) authentication.getPrincipal()).getUsername();

        return userRepository.findByUsername(username)
                .orElseThrow(BudgetUserNotFoundException::new);
    }
}
