package org.springframework.data.influxdb.core.mapping;

import org.springframework.data.mapping.PersistentEntity;

public interface InfluxDBPersistentEntity<T> extends PersistentEntity<T, InfluxDBPersistentProperty> {

}
