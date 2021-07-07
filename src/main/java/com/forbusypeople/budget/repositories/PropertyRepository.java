package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, UUID> {
    List<PropertyEntity> findAllByUser(UserEntity user);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE PropertyEntity e SET e.sold=true WHERE e.user = :user AND e.id = :id")
    void setSoldProperty(UserEntity user,
                         UUID id);
}
