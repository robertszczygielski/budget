package com.forbusypeople.budget.mappers;

import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.UserDetailsDto;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity fromDtoToEntity(UserDetailsDto userDetailsDto) {
        var entity = new UserEntity();

        entity.setPassword(encodePassword(userDetailsDto.getPassword()));
        entity.setUsername(userDetailsDto.getUsername());

        return entity;
    }

    private String encodePassword(String password) {
        var salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
