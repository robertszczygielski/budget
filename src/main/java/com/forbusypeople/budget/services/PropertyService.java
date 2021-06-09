package com.forbusypeople.budget.services;

import com.forbusypeople.budget.repositories.PropertyRepository;
import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserLogInfoService userLogInfoService;

    public PropertyService(PropertyRepository propertyRepository,
                           UserLogInfoService userLogInfoService) {
        this.propertyRepository = propertyRepository;
        this.userLogInfoService = userLogInfoService;
    }

    public UUID addProperty(PropertyDto dto) {
        UserEntity user = userLogInfoService.getLoggedUserEntity();
        PropertyEntity entity = new PropertyEntity();

        entity.setUser(user);
        entity.setRooms(dto.getRooms());
        entity.setStreet(dto.getStreet());
        entity.setSingle(dto.getSingle());
        entity.setHouse(dto.getHouse());
        entity.setCity(dto.getCity());
        entity.setPostCode(dto.getPostCode());

        var saveEntity = propertyRepository.save(entity);
        return saveEntity.getId();
    }
}
