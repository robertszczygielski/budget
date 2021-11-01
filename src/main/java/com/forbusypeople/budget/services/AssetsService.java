package com.forbusypeople.budget.services;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.enums.FilterSpecification;
import com.forbusypeople.budget.filters.FilterRangeStrategy;
import com.forbusypeople.budget.mappers.AssetsMapper;
import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.currency.CurrencyService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.users.UserLogInfoService;
import com.forbusypeople.budget.validators.AssetValidator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssetsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsService.class.getName());

    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;
    private final AssetValidator assetValidator;
    private final UserLogInfoService userLogInfoService;
    private final FilterRangeStrategy<AssetEntity> filterRangeStrategy;
    private final CurrencyService currencyService;
    private final String DEFAULT_CURRENCY = "PLN";

    public List<AssetDto> getAllAssets() {
        LOGGER.debug("Get all assets");
        var user = getUserEntity();

        return assetsRepository.getAssetEntitiesByUser(user)
                .stream()
                .map(entity -> assetsMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    @Transactional
    public void setAsset(List<AssetDto> dtos) {
        LOGGER.info("Set Asset");
        var user = getUserEntity();

        dtos.forEach(dto -> {
            assetValidator.validate(dto);
            changeCurrencyIfNecessary(dto);
            var entity = assetsMapper.fromDtoToEntity(dto, user);
            assetsRepository.save(entity);
        });

    }

    private void changeCurrencyIfNecessary(AssetDto dto) {
        if (Objects.isNull(dto.getCurrencyCode())) return;
        if (dto.getCurrencyCode().isBlank()) return;
        if (dto.getCurrencyCode().equals(DEFAULT_CURRENCY)) return;

        var currencyCode = dto.getCurrencyCode().toUpperCase();
        BigDecimal amount = dto.getAmount();
        var currencyDto = currencyService.getCurrencyFromNbp(currencyCode);
        var currencyAmount = currencyDto.getRates()
                .stream()
                .findFirst()
                .get()
                .getMid();

        dto.setAmount(amount.multiply(currencyAmount));
    }

    public void deleteAsset(AssetDto dto) {
        LOGGER.info("Delete asset");
        LOGGER.debug("AssetDto: " + dto);
        var user = getUserEntity();
        var entity = assetsMapper.fromDtoToEntity(dto, user);
        assetsRepository.delete(entity);
        LOGGER.info("Asset deleted");
    }

    public void updateAsset(AssetDto dto) {
        LOGGER.info("Update asset");
        LOGGER.debug("AssetDto: " + dto);
        var entity = assetsRepository.findById(dto.getId());
        entity.ifPresent(System.out::println);
        entity.ifPresent(e -> {
            e.setAmount(dto.getAmount());
            System.out.println(e);
            assetsRepository.saveAndFlush(e);
        });

        LOGGER.info("Asset updated");
    }

    public List<AssetDto> getAssetsByCategory(AssetCategory category) {
        return assetsRepository.getAssetEntitiesByCategory(category)
                .stream()
                .map(entity -> assetsMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public void deleteAssetByUser(UserEntity userEntity) {
        assetsRepository.deleteAllByUser(userEntity);
    }

    public List<AssetDto> getAssetsByFilter(Map<String, String> filter) {
        var user = userLogInfoService.getLoggedUserEntity();
        return getAssetsByFilter(user, filter);
    }

    public List<AssetDto> getAssetsByFilter(UserEntity user,
                                            Map<String, String> filter) {
        FilterSpecification specification = FilterSpecification.FOR_ASSETS;

        return filterRangeStrategy.getFilteredDataForSpecification(user, filter, specification)
                .stream()
                .map(entity -> assetsMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    private UserEntity getUserEntity() {
        LOGGER.info("getLoggedUserEntity");
        return userLogInfoService.getLoggedUserEntity();
    }
}
