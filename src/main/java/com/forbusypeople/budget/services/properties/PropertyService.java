package com.forbusypeople.budget.services.properties;

import com.forbusypeople.budget.mappers.PropertyMapper;
import com.forbusypeople.budget.repositories.PropertyRepository;
import com.forbusypeople.budget.repositories.RoomsRepository;
import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.AssociationPropertyRoomService;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import com.forbusypeople.budget.services.users.UserLogInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserLogInfoService userLogInfoService;
    private final PropertyMapper propertyMapper;
    private final RoomsRepository roomsRepository;
    private final AssociationPropertyRoomService associationPropertyRoomService;

    public UUID addProperty(PropertyDto dto) {
        UserEntity user = userLogInfoService.getLoggedUserEntity();
        PropertyEntity entity = propertyMapper.fromDtoToEntity(dto, user);

        var saveEntity = propertyRepository.save(entity);
        return saveEntity.getId();
    }

    public List<PropertyDto> findAllProperties(boolean isSold) {
        var user = userLogInfoService.getLoggedUserEntity();
        return propertyRepository.findAllByUser(user, isSold)
                .stream()
                .map(entity -> associationPropertyRoomService.setAdditionalDataForRoomInProperty(entity))
                .map(entity -> propertyMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProperty(PropertyDto dto) {
        updateOnlyProperty(dto);
        updateRentRoomsInProperty(dto);
        updateCurrencyRoomInProperty(dto);
    }

    @Transactional
    public void setSoldProperty(UUID id) {
        var user = userLogInfoService.getLoggedUserEntity();
        propertyRepository.setSoldProperty(user, id);
    }

    private void updateRentRoomsInProperty(PropertyDto dto) {
        associationPropertyRoomService.setRentRooms(dto);
    }

    private void updateCurrencyRoomInProperty(PropertyDto dto) {
        associationPropertyRoomService.setCurrencyRooms(dto);
    }

    private void updateOnlyProperty(PropertyDto dto) {
        var entity = propertyRepository.findById(dto.getId()).stream().findFirst();
        if (entity.isPresent()) {
            var entityToChange = entity.get();
            var roomsEntity = dto.getRooms().stream()
                    .map(it -> roomsRepository.findById(it.getId()).get())
                    .collect(Collectors.toList());

            propertyMapper.updateEntityByDto(entityToChange, dto, roomsEntity);
        }
    }
}
