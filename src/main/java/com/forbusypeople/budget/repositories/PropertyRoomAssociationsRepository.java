package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.PropertyRoomAssociationsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyRoomAssociationsRepository extends CrudRepository<PropertyRoomAssociationsEntity, UUID> {

    Optional<List<PropertyRoomAssociationsEntity>> getAssociationsByPropertyId(UUID propertyId);

    PropertyRoomAssociationsEntity setRent(UUID propertyId,
                                           UUID roomId,
                                           Boolean isRent);

}
