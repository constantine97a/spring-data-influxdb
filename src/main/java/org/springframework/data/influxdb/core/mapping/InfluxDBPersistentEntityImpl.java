package org.springframework.data.influxdb.core.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;

import java.util.Comparator;

/**
 * InfluxDBPersistentEntityImpl that implement {@link InfluxDBPersistentEntity}
 *
 * @param <T> entity clazz
 */
public class InfluxDBPersistentEntityImpl<T> extends BasicPersistentEntity<T, InfluxDBPersistentProperty>
        implements InfluxDBPersistentEntity<T> {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDBPersistentEntity.class);


    public InfluxDBPersistentEntityImpl(TypeInformation<T> information, Comparator<InfluxDBPersistentProperty> comparator) {
        super(information, comparator);
    }

}
