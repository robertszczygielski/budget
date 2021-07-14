package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.PropertyRoomAssociationsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyRoomAssociationsRepository extends CrudRepository<PropertyRoomAssociationsEntity, UUID> {
}
