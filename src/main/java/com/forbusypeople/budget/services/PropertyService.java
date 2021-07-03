package com.forbusypeople.budget.services;

import com.forbusypeople.budget.mappers.PropertyMapper;
import com.forbusypeople.budget.repositories.PropertyRepository;
import com.forbusypeople.budget.repositories.entities.PropertyEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.dtos.PropertyDto;
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

    public UUID addProperty(PropertyDto dto) {
        UserEntity user = userLogInfoService.getLoggedUserEntity();
        PropertyEntity entity = propertyMapper.fromDtoToEntity(dto, user);

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

    @Transactional
    public void updateProperty(PropertyDto dto) {
        var entity = propertyRepository.findById(dto.getId()).stream().findFirst();
        if (entity.isPresent()) {
            var entityToChange = entity.get();
            propertyMapper.updateEntityByDto(entityToChange, dto);
        }
    }
}
