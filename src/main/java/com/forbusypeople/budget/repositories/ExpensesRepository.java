package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpensesRepository extends JpaRepository<ExpensesEntity, UUID> {
    List<ExpensesEntity> findAllByUser(UserEntity user);
}
