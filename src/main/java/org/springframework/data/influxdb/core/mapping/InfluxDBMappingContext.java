package org.springframework.data.influxdb.core.mapping;

import org.springframework.data.influxdb.annotation.Point;
import org.springframework.data.influxdb.annotation.Timestamp;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InfluxDBMappingContext extends AbstractMappingContext<SimpleInfluxDBPersistentEntity<?>, InfluxDBPersistentProperty> {

    private static final FieldNamingStrategy DEFAULT_NAMING_STRATEGY = PropertyNameFieldNamingStrategy.INSTANCE;

    private FieldNamingStrategy fieldNamingStrategy = DEFAULT_NAMING_STRATEGY;

    /**
     * Configures the {@link FieldNamingStrategy} to be used to determine the field name if no manual mapping is applied.
     * Defaults to a strategy using the plain property name.
     *
     * @param fieldNamingStrategy the {@link FieldNamingStrategy} to be used to determine the field name if no manual
     *                            mapping is applied.
     * @since 4.2
     */
    public void setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
        this.fieldNamingStrategy = fieldNamingStrategy;
    }

    /**
     * Returns whether a {@link PersistentEntity} instance should be created for the given {@link TypeInformation}. By
     * default this will reject all types considered simple and non-supported Kotlin classes, but it might be necessary to
     * tweak that in case you have registered custom converters for top level types (which renders them to be considered
     * simple) but still need meta-information about them.
     *
     * @param type will never be {@literal null}.
     * @return true if PersistentEntity needed to created,otherwise false
     */
    @Override
    protected boolean shouldCreatePersistentEntityFor(TypeInformation<?> type) {

        boolean hasTimestampKey = false;


        for (Method method : type.getType().getMethods()) {
            if (method.isAnnotationPresent(Timestamp.class)) {
                hasTimestampKey = true;
                break;
            }
        }
        for (Field field : type.getType().getFields()) {
            if (field.isAnnotationPresent(Timestamp.class)) {
                hasTimestampKey = true;
            }
        }
        return type.getType().isAnnotationPresent(Point.class) || hasTimestampKey;
    }

    @Override
    protected <T> SimpleInfluxDBPersistentEntity<?> createPersistentEntity(TypeInformation<T> typeInformation) {
        return new SimpleInfluxDBPersistentEntity<>(typeInformation, new SimpleInfluxDBPersistentEntity.ContextConfiguration(this.fieldNamingStrategy));
    }

    @Override
    protected SimpleInfluxDBPersistentProperty createPersistentProperty(Property property, SimpleInfluxDBPersistentEntity<?> owner, SimpleTypeHolder simpleTypeHolder) {
        return new SimpleInfluxDBPersistentProperty(property, owner, simpleTypeHolder);
    }


}
