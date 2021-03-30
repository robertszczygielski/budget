package com.forbusypeople.budget.services;

import com.forbusypeople.budget.excetpions.BudgetUserAlreadyExistsInDatabaseException;
import com.forbusypeople.budget.excetpions.BudgetUserNotFoundException;
import com.forbusypeople.budget.mappers.UserMapper;
import com.forbusypeople.budget.repositories.UserRepository;
import com.forbusypeople.budget.services.dtos.UserDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Searching user = " + username);
        var entity = userRepository
                .findByUsername(username)
                .orElseThrow(BudgetUserNotFoundException::new);

        return new User(entity.getUsername(), entity.getPassword(), Collections.emptyList());
    }

    public UUID saveUser(UserDetailsDto userDetailsDto) {
        validateIfUserExists(userDetailsDto);
        var entity = userMapper.fromDtoToEntity(userDetailsDto);
        var savedEntity = userRepository.save(entity);
        LOGGER.info("User saved = " + savedEntity);

        return savedEntity.getId();
    }

    private void validateIfUserExists(UserDetailsDto userDetailsDto) {
        var entity = userRepository.findByUsername(userDetailsDto.getUsername());

        if (entity.isPresent()) {
            throw new BudgetUserAlreadyExistsInDatabaseException();
        }
    }
}
