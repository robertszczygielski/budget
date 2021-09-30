package com.forbusypeople.budget.services.users;

import com.forbusypeople.budget.mappers.AdditionalUserDataMapper;
import com.forbusypeople.budget.repositories.AdditionalUserDataRepository;
import com.forbusypeople.budget.services.dtos.AdditionalUserDataDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdditionalUserDataService {

    private final AdditionalUserDataMapper additionalUserDataMapper;
    private final AdditionalUserDataRepository additionalUserDataRepository;
    private final UserLogInfoService userLogInfoService;

    public void saveAdditionalData(AdditionalUserDataDto dto) {
        var user = userLogInfoService.getLoggedUserEntity();
        var entity = additionalUserDataMapper.fromDtoToEntity(dto, user);
        additionalUserDataRepository.save(entity);
    }

    public AdditionalUserDataDto getAdditionalData() {
        var user = userLogInfoService.getLoggedUserEntity();
        var entity = additionalUserDataRepository.findAdditionalUserDataEntitiesByUser(user);
        return additionalUserDataMapper.fromEntityToDto(entity);
    }

}
