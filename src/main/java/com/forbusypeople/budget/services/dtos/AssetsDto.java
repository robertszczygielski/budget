package com.forbusypeople.budget.services.dtos;

import java.util.List;
import java.util.Objects;

public class AssetsDto {

    private List<Integer> assets;

    public List<Integer> getAssets() {
        return assets;
    }

    public void setAssets(List<Integer> assets) {
        this.assets = assets;
    }

    @Override
    public String toString() {
        return "AssetsDto{" +
                "assets=" + assets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetsDto assetsDto = (AssetsDto) o;
        return Objects.equals(assets, assetsDto.assets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assets);
    }
}
