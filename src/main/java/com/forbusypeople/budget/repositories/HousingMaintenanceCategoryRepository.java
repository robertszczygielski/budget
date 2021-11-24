package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.HousingMaintenanceCategoryEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HousingMaintenanceCategoryRepository extends JpaRepository<HousingMaintenanceCategoryEntity, UUID> {
    List<HousingMaintenanceCategoryEntity> findAllByUser(UserEntity user);
}
