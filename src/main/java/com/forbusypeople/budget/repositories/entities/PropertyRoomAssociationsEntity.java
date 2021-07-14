package com.forbusypeople.budget.repositories.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "property_room_associations")
@Data
@IdClass(PropertyRoomAssociationsEntity.class)
public class PropertyRoomAssociationsEntity implements Serializable {

    @Id
    private UUID property_id;
    @Id
    private UUID room_id;
    private Boolean rent;
}
