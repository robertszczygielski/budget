package com.forbusypeople.budget.repositories;

import com.forbusypeople.budget.repositories.entities.PropertyRoomAssociationsEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyRoomAssociationsRepository extends CrudRepository<PropertyRoomAssociationsEntity, UUID> {

    Optional<List<PropertyRoomAssociationsEntity>> getAssociationsByPropertyId(UUID propertyId);

    @Modifying(clearAutomatically = true)
    void setRent(UUID propertyId,
                 UUID roomId,
                 Boolean isRent);

    @Modifying(clearAutomatically = true)
    void setCurrency(UUID propertyId,
                     UUID roomId,
                     String currency);

}
