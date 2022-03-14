package org.springframework.data.influxdb.repository.support;

import org.springframework.data.influxdb.core.mapping.InfluxDBPersistentEntity;
import org.springframework.data.influxdb.core.mapping.InfluxDBPersistentProperty;
import org.springframework.data.mapping.context.MappingContext;

/**
 * InfluxDBEntityInformationCreatorImpl
 *
 * @author yuri.yin
 */
public class InfluxDBEntityInformationCreatorImpl implements InfluxDBEntityInformationCreator {

    public InfluxDBEntityInformationCreatorImpl(MappingContext<? extends InfluxDBPersistentEntity<?>, InfluxDBPersistentProperty> mappingContext) {

    }

    @Override
    public <T, ID> InfluxDBEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return null;
    }
}
