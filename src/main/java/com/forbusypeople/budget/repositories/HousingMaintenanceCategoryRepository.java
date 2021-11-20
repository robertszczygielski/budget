package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.HousingMaintenanceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HousingMaintenanceCategoryRepository extends JpaRepository<HousingMaintenanceCategoryEntity, UUID> {
}
