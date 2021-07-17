package com.forbusypeople.budget.services;

import com.forbusypeople.budget.repositories.PropertyRoomAssociationsRepository;
import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.PropertyRoomAssociationsEntity;
import com.forbusypeople.budget.repositories.entities.RoomsEntity;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {

    private final PropertyRoomAssociationsRepository associations;

    public PropertyEntity setRentRoomInProperty(PropertyEntity propertyEntity) {
        var optionalPropertyRoom =
                associations.getAssociationsByPropertyId(propertyEntity.getId());

        if (optionalPropertyRoom.isPresent()) {
            var associationsEntity = optionalPropertyRoom.get();
            propertyEntity.getRooms()
                    .forEach(room -> setRentForRoom(room, associationsEntity));
        }

        return propertyEntity;
    }

    public void setRentRooms(PropertyDto dto) {
        var propertyId = dto.getId();
        var roomsList = dto.getRooms();

        roomsList.forEach(
                room -> associations.setRent(propertyId, room.getId(), room.getRent())
        );
    }

    public RoomsEntity setRentRooms(RoomsEntity roomsEntity,
                                    List<PropertyRoomAssociationsEntity> associationsEntities) {
        var associationsEntity = associationsEntities.stream()
                .filter(association -> association.getRoomId().equals(roomsEntity.getId()))
                .findFirst()
                .get();

        roomsEntity.setRent(associationsEntity.getRent());

        return roomsEntity;
    }


    private RoomsEntity setRentForRoom(RoomsEntity room,
                                       List<PropertyRoomAssociationsEntity> associationsEntity) {
        PropertyRoomAssociationsEntity propertyRoomAssociationsEntity = associationsEntity.stream()
                .filter(it -> it.getRoomId().equals(room.getId()))
                .findFirst()
                .get();

        room.setRent(propertyRoomAssociationsEntity.getRent());
        return room;
    }

}
