package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface AssetsRepository extends JpaRepository<AssetEntity, UUID> {

    List<AssetEntity> getAssetEntitiesByCategory(AssetCategory category);

    List<AssetEntity> getAssetEntitiesByUser(UserEntity userEntity);

    void deleteAllByUser(UserEntity userEntity);

    @Query("SELECT e FROM AssetEntity e " +
            "WHERE e.user = :user " +
            "AND e.incomeDate >= :fromDate " +
            "AND e.incomeDate <= :toDate " +
            "AND e.category in (:categories)")
    List<AssetEntity> findAllByBetweenDate(UserEntity user,
                                           Instant fromDate,
                                           Instant toDate,
                                           List<AssetCategory> categories);
}
