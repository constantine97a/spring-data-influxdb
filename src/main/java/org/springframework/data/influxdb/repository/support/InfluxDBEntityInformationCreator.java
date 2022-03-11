package org.springframework.data.influxdb.repository.support;

/**
 * InfluxDBEntityInformationCreator
 *
 * @author yuri.yin
 */
public interface InfluxDBEntityInformationCreator {

    <T, ID> InfluxDBEntityInformation<T, ID> getEntityInformation(Class<T> domainClass);

}
