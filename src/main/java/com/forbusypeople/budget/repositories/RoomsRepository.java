package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.RoomsEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomsRepository extends JpaRepository<RoomsEntity, UUID> {
    Optional<List<RoomsEntity>> findAllByUser(UserEntity user);
}
