package org.springframework.data.influxdb.repository.support;

import org.springframework.data.influxdb.core.InfluxDBOperations;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;


/**
 * Factory to create {@link org.springframework.data.influxdb.repository.InfluxDBRepository}
 *
 * @author Yuri.Yin
 */
public class InfluxDBRepositoryFactory extends RepositoryFactorySupport {

    private final InfluxDBOperations influxDBOperations;




    public InfluxDBRepositoryFactory(InfluxDBOperations influxDBOperations) {
        this.influxDBOperations = influxDBOperations;
    }

    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return null;
    }


    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getTargetRepository(RepositoryInformation)
     */
    @Override
    protected Object getTargetRepository(RepositoryInformation metadata) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getRepositoryBaseClass(RepositoryMetadata)
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return null;
    }
}
