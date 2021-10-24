package com.forbusypeople.budget.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "property_room_associations")
@Data
@IdClass(PropertyRoomAssociationsEntity.class)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getAssociationsByPropertyId",
                query = "SELECT * FROM property_room_associations WHERE property_id = ?1",
                resultClass = PropertyRoomAssociationsEntity.class
        ),
        @NamedNativeQuery(
                name = "PropertyRoomAssociationsEntity.setRent",
                query = "UPDATE property_room_associations SET rent = ?3 WHERE property_id = ?1 AND room_id = ?2"
        ),
        @NamedNativeQuery(
                name = "PropertyRoomAssociationsEntity.setCurrency",
                query = "UPDATE property_room_associations SET currency = ?3 WHERE property_id = ?1 AND room_id = ?2"
        )
})
public class PropertyRoomAssociationsEntity implements Serializable {

    @Id
    @Column(name = "property_id")
    private UUID propertyId;
    @Id
    @Column(name = "room_id")
    private UUID roomId;
    private Boolean rent;
    private String currency;
}
