package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.ExpensesEstimatedPercentageEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpensesEstimatePercentageRepository extends CrudRepository<ExpensesEstimatedPercentageEntity, UUID> {

    Optional<List<ExpensesEstimatedPercentageEntity>> findAllByUser(UserEntity user);

}
