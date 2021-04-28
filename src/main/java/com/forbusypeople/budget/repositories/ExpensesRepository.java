package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ExpensesRepository extends JpaRepository<ExpensesEntity, UUID> {
    List<ExpensesEntity> findAllByUser(UserEntity user);

    @Query("SELECT e FROM ExpensesEntity e WHERE e.user = :user AND e.purchaseDate >= :fromDate AND e.purchaseDate <= :toDate")
    List<ExpensesEntity> findAllByBetweenDate(UserEntity user, Instant fromDate, Instant toDate);
}
