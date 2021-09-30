package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.AdditionalUserDataEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdditionalUserDataRepository extends CrudRepository<AdditionalUserDataEntity, UUID> {

    AdditionalUserDataEntity findAdditionalUserDataEntitiesByUser(UserEntity user);

}
