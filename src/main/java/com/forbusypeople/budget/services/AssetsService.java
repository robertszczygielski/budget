package com.forbusypeople.budget.services;

import com.forbusypeople.budget.mappers.AssetsMapper;
import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.validators.AssetValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsService.class.getName());

    private AssetsRepository assetsRepository;
    private AssetsMapper assetsMapper;
    private AssetValidator assetValidator;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper, AssetValidator assetValidator) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
        this.assetValidator = assetValidator;
    }

    public List<AssetDto> getAllAssets() {
        LOGGER.debug("Get all assets");
        return assetsRepository.findAll().stream()
                .map(entity -> assetsMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public void setAsset(AssetDto dto) {
        LOGGER.info("Set Asset");
        LOGGER.debug("AssetDto: " + dto);
        assetValidator.validate(dto);
        var entity = assetsMapper.fromDtoToEntity(dto);

        assetsRepository.save(entity);
        LOGGER.info("Asset Saved");
    }

    public void deleteAsset(AssetDto dto) {
        LOGGER.info("Delete asset");
        LOGGER.debug("AssetDto: " + dto);
        var entity = assetsMapper.fromDtoToEntity(dto);
        assetsRepository.delete(entity);
        LOGGER.info("Asset deleted");
    }

    public void updateAsset(AssetDto dto) {
        LOGGER.info("Update asset");
        LOGGER.debug("AssetDto: " + dto);
        var entity = assetsRepository.findById(dto.getId());
        entity.ifPresent(e -> {
            e.setAmount(dto.getAmount());
            assetsRepository.saveAndFlush(e);
        });
        LOGGER.info("Asset updated");
    }
}
