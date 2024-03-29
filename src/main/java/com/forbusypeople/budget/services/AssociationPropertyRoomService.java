package com.forbusypeople.budget.services;

import com.forbusypeople.budget.repositories.PropertyRoomAssociationsRepository;
import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.PropertyRoomAssociationsEntity;
import com.forbusypeople.budget.repositories.entities.RoomsEntity;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AssociationPropertyRoomService {

    private final PropertyRoomAssociationsRepository associations;

    public PropertyEntity setAdditionalDataForRoomInProperty(PropertyEntity propertyEntity) {
        var optionalPropertyRoom =
                associations.getAssociationsByPropertyId(propertyEntity.getId());

        if (optionalPropertyRoom.isPresent()) {
            var associationsEntity = optionalPropertyRoom.get();
            propertyEntity.getRooms()
                    .forEach(room -> setAdditionalDataForRoom(room, associationsEntity));
        }

        return propertyEntity;
    }

    public void setRentRooms(PropertyDto dto) {
        var propertyId = dto.getId();
        var roomsList = dto.getRooms();

        roomsList.forEach(
                room -> associations.setRent(propertyId, room.getId(), setRentValueOrFalse(room.getRent()))
        );
    }

    public void setCurrencyRooms(PropertyDto dto) {
        var propertyId = dto.getId();
        var roomsList = dto.getRooms();

        roomsList.forEach(
                room ->
                        associations.setCurrency(
                                propertyId,
                                room.getId(),
                                setCurrencyOrDefault(room.getCurrency())
                        )
        );
    }

    private String setCurrencyOrDefault(String currency) {
        return Objects.nonNull(currency) ? currency : "PLN";
    }

    private Boolean setRentValueOrFalse(Boolean isRoom) {
        return Objects.nonNull(isRoom) ? isRoom : false;
    }

    private RoomsEntity setAdditionalDataForRoom(RoomsEntity room,
                                                 List<PropertyRoomAssociationsEntity> associationsEntity) {
        PropertyRoomAssociationsEntity propertyRoomAssociationsEntity = associationsEntity.stream()
                .filter(it -> it.getRoomId().equals(room.getId()))
                .findFirst()
                .get();

        room.setRent(propertyRoomAssociationsEntity.getRent());
        room.setCurrency(propertyRoomAssociationsEntity.getCurrency());

        return room;
    }

}
