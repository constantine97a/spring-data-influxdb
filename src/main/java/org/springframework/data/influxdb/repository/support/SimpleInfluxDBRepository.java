package org.springframework.data.influxdb.repository.support;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.influxdb.core.InfluxDBOperations;
import org.springframework.data.influxdb.core.mapping.InfluxDBPersistentEntity;
import org.springframework.data.influxdb.repository.InfluxDBRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SimpleInfluxDBRepository<T, ID> implements InfluxDBRepository<T, ID> {

    protected InfluxDBOperations<T> operations;

    protected InfluxDBEntityInformation<T, ID> entityInformation;

    private final Class<T> entityClass;

    /**
     * default constructor
     *
     * @param metadata   {@link InfluxDBEntityInformation }
     * @param operations {@link InfluxDBOperations}
     */
    public SimpleInfluxDBRepository(InfluxDBEntityInformation<T, ID> metadata,
                                    InfluxDBOperations<T> operations) {
        this.operations = operations;
        Assert.notNull(metadata, "InfluxDBEntityInformation must not be null!");
        this.entityInformation = metadata;
        this.entityClass = this.entityInformation.getJavaType();
        if (this.shouldCreateDatabase()) {
            this.operations.createDatabase();
        }
    }


    private boolean shouldCreateDatabase() {
        InfluxDBPersistentEntity<?> entity = operations.getInfluxDBConverter().getMappingContext().getRequiredPersistentEntity(this.entityClass);
        return entity.isCreateDatabase();
    }


    @Override
    public <S extends T> S save(S entity) {
        return null;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(ID timestamp) {
        return false;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> timestamps) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(ID timestamp) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends ID> timestamps) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends T> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @FunctionalInterface
    public interface OperationsCallback<R> {

        @Nullable
        R doWithOperations(InfluxDBOperations operations);
    }


    @Nullable
    public <R> R execute(OperationsCallback<R> callback) {
        return callback.doWithOperations(operations);
    }
}
