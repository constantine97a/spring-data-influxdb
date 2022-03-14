package org.springframework.data.influxdb.core.convert;

import org.springframework.data.convert.EntityConverter;
import org.springframework.data.influxdb.annotation.Point;
import org.springframework.data.influxdb.core.mapping.InfluxDBPersistentEntity;
import org.springframework.data.influxdb.core.mapping.InfluxDBPersistentProperty;

/**
 * @author yuri.yin
 */
public interface InfluxDBConverter extends
        EntityConverter<InfluxDBPersistentEntity<?>, InfluxDBPersistentProperty, Object, Point> {

}
