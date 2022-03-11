package org.springframework.data.influxdb.repository.support;

/**
 * InfluxDBEntityInformationCreatorImpl
 *
 * @author yuri.yin
 */
public class InfluxDBEntityInformationCreatorImpl implements InfluxDBEntityInformationCreator {

    @Override
    public <T, ID> InfluxDBEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return null;
    }
}
