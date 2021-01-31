package com.forbusypeople.budget.services;

import com.forbusypeople.budget.mappers.AssetsMapper;
import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.AssetsDto;
import com.forbusypeople.budget.validators.AssertValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class AssetsService {

    private AssetsRepository assetsRepository;
    private AssetsMapper assetsMapper;
    private AssertValidator assertValidator;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper, AssertValidator assertValidator) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
        this.assertValidator = assertValidator;
    }

    public AssetsDto getAllAssets() {
        var assetsEntity = assetsRepository.findAll();
        var assetsAmount = assetsEntity.stream()
                .map(entity -> entity.getAmount().intValue())
                .collect(Collectors.toList());
        var dto = new AssetsDto();
        dto.setAssets(assetsAmount);
        return dto;

    }

    public void setAsset(AssetDto dto) {
        assertValidator.validate(dto);
        var entity = assetsMapper.fromDtoToEntity(dto);

        assetsRepository.save(entity);
    }

    public void deleteAsset(AssetDto dto) {
        var entity = assetsMapper.fromDtoToEntity(dto);
        assetsRepository.delete(entity);
    }
}
