package com.forbusypeople.budget.services;

import com.forbusypeople.budget.mappers.AssetsMapper;
import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.AssetsDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class AssetsService {

    private AssetsRepository assetsRepository;
    private AssetsMapper assetsMapper;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
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

    public void setAsset(int asset) {
        var dot = new AssetDto();
        dot.setAmount(new BigDecimal(asset));
        var entity = assetsMapper.fromDtoToEntity(dot);

        assetsRepository.save(entity);
    }
}
