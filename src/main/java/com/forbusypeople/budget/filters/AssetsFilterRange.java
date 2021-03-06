package com.forbusypeople.budget.filters;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component("forAssetsRange")
class AssetsFilterRange extends FilterRangeAbstract<AssetEntity> {

    private final AssetsRepository assetsRepository;

    public AssetsFilterRange(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    @Override
    protected List<AssetEntity> getAllEntityBetweenDate(UserEntity user,
                                                        Instant fromDate,
                                                        Instant toDate,
                                                        String category) {
        return assetsRepository.findAllByBetweenDate(user, fromDate, toDate, mapStrongToEnum(category));
    }

    private List<AssetCategory> mapStrongToEnum(String category) {
        if (Objects.isNull(category)) {
            return Arrays.asList(AssetCategory.values());
        }
        return Arrays.asList(AssetCategory.valueOf(category.toUpperCase()));
    }

}
