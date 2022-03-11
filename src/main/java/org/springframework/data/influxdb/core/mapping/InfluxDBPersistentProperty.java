package org.springframework.data.influxdb.core.mapping;

import org.springframework.data.mapping.PersistentProperty;

public interface InfluxDBPersistentProperty extends PersistentProperty<InfluxDBPersistentProperty> {

    String getFieldName();


}
