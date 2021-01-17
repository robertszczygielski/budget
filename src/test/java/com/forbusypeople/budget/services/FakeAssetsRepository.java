package com.forbusypeople.budget.services;

import com.forbusypeople.budget.repositories.AssetsRepository;
import com.forbusypeople.budget.repositories.entities.AssetEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FakeAssetsRepository implements AssetsRepository {
    @Override
    public List<AssetEntity> findAll() {
        return null;
    }

    @Override
    public List<AssetEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AssetEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<AssetEntity> findAllById(Iterable<UUID> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(AssetEntity entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends AssetEntity> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends AssetEntity> S save(S s) {
        return null;
    }

    @Override
    public <S extends AssetEntity> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<AssetEntity> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AssetEntity> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<AssetEntity> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AssetEntity getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends AssetEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AssetEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AssetEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AssetEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AssetEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AssetEntity> boolean exists(Example<S> example) {
        return false;
    }
}
