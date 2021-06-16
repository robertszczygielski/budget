package com.forbusypeople.budget.services;

import com.forbusypeople.budget.mappers.PropertyMapper;
import com.forbusypeople.budget.repositories.PropertyRepository;
import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserLogInfoService userLogInfoService;
    private final PropertyMapper propertyMapper;

    public PropertyService(PropertyRepository propertyRepository,
                           UserLogInfoService userLogInfoService,
                           PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.userLogInfoService = userLogInfoService;
        this.propertyMapper = propertyMapper;
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

    public List<PropertyDto> findAllProperties() {
        var user = userLogInfoService.getLoggedUserEntity();
        return propertyRepository.findAllByUser(user)
                .stream()
                .map(entity -> propertyMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public void deleteProperty(PropertyDto dto) {
        var entity = propertyRepository.findById(dto.getId()).stream().findFirst();
        if (entity.isPresent()) {
            propertyRepository.delete(entity.get());
        }
    }

    public void updateProperty(PropertyDto dto) {
        var entity = propertyRepository.findById(dto.getId()).stream().findFirst();
        if (entity.isPresent()) {
            var newEntity = propertyMapper.updateEntityByDto(entity.get(), dto);
            propertyRepository.saveAndFlush(newEntity);
        }
    }
}
