package org.springframework.data.influxdb.core.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.util.TypeInformation;

/**
 * InfluxDBPersistentEntityImpl that implement {@link InfluxDBPersistentEntity}
 *
 * @param <T> entity clazz
 */
public class SimpleInfluxDBPersistentEntity<T> extends BasicPersistentEntity<T, InfluxDBPersistentProperty>
        implements InfluxDBPersistentEntity<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfluxDBPersistentEntity.class);

    private ContextConfiguration contextConfiguration;

    public SimpleInfluxDBPersistentEntity(TypeInformation<T> typeInformation, ContextConfiguration contextConfiguration) {
        super(typeInformation);
        this.contextConfiguration = contextConfiguration;

    }

    /**
     * Configuration settings passed in from the creating {@link InfluxDBMappingContext}.
     */
    static class ContextConfiguration {

        private final FieldNamingStrategy fieldNamingStrategy;

        ContextConfiguration(FieldNamingStrategy fieldNamingStrategy) {
            this.fieldNamingStrategy = fieldNamingStrategy;
        }

        public FieldNamingStrategy getFieldNamingStrategy() {
            return fieldNamingStrategy;
        }
    }

}
