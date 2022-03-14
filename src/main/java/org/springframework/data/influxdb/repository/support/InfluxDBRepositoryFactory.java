package org.springframework.data.influxdb.repository.support;

import org.springframework.data.influxdb.core.InfluxDBOperations;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.lang.Nullable;

import java.util.Optional;


/**
 * Factory to create {@link org.springframework.data.influxdb.repository.InfluxDBRepository}
 *
 * @author Yuri.Yin
 */
public class InfluxDBRepositoryFactory extends RepositoryFactorySupport {

    private final InfluxDBOperations influxDBOperations;

    /**
     * actually as {@link org.springframework.data.mapping.context.MappingContext} wrapper
     */
    private final InfluxDBEntityInformationCreator entityInformationCreator;


    public InfluxDBRepositoryFactory(InfluxDBOperations influxDBOperations) {

        this(influxDBOperations, null);

    }

    public InfluxDBRepositoryFactory(InfluxDBOperations influxDBOperations, @Nullable InfluxDBEntityInformationCreator influxDBEntityInformationCreator) {
        this.influxDBOperations = influxDBOperations;
        if (influxDBEntityInformationCreator != null) {
            this.entityInformationCreator = influxDBEntityInformationCreator;
        } else {
            this.entityInformationCreator = new InfluxDBEntityInformationCreatorImpl(this.influxDBOperations.getInfluxDBConverter().getMappingContext());
        }
    }

    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return this.entityInformationCreator.getEntityInformation(domainClass);
    }


    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getTargetRepository(RepositoryInformation)
     */
    @Override
    protected Object getTargetRepository(RepositoryInformation metadata) {
        EntityInformation<?, Object> entityInformation = this.getEntityInformation(metadata.getDomainType());
        return getTargetRepositoryViaReflection(metadata, entityInformation, this.influxDBOperations);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getRepositoryBaseClass(RepositoryMetadata)
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleInfluxDBRepository.class;
    }

    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(QueryLookupStrategy.Key key, QueryMethodEvaluationContextProvider evaluationContextProvider) {
        return super.getQueryLookupStrategy(key, evaluationContextProvider);
    }
}
