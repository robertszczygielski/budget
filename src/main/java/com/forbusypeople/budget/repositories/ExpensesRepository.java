package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpensesRepository extends JpaRepository<ExpensesEntity, UUID> {
}
