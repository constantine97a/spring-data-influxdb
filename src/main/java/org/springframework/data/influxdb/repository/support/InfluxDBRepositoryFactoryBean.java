package org.springframework.data.influxdb.repository.support;

import org.springframework.data.influxdb.core.InfluxDBOperations;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;

public class InfluxDBRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
        extends RepositoryFactoryBeanSupport<T, S, ID> {

    @Nullable
    private InfluxDBOperations operations;

    /**
     * Creates a new {@link RepositoryFactoryBeanSupport} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    protected InfluxDBRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        Assert.notNull(this.operations, "operations are not initialized");
        return new InfluxDBRepositoryFactory(operations);
    }

    /**
     * Configures the {@link InfluxDBOperations} to be used to create InfluxDB repositories
     *
     * @param operations the operation to set
     */
    public void setInfluxDBOperations(InfluxDBOperations operations) {
        Assert.notNull(operations, "InfluxDBOperations must not be null");
//        setMappingContext(operations.getElasticsearchConverter().getMappingContext());
        this.operations = operations;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Assert.notNull(this.operations, "operations are not initialized");
    }
}
