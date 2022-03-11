package org.springframework.data.influxdb.core.mapping;

import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;

public class SimpleInfluxDBPersistentProperty extends AnnotationBasedPersistentProperty<InfluxDBPersistentProperty>
        implements InfluxDBPersistentProperty {


    /**
     * Creates a new {@link AnnotationBasedPersistentProperty}.
     *
     * @param property         must not be {@literal null}.
     * @param owner            must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public SimpleInfluxDBPersistentProperty(Property property, PersistentEntity<?, InfluxDBPersistentProperty> owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
    }

    @Override
    public String getFieldName() {
        return null;
    }

    @Override
    protected Association<InfluxDBPersistentProperty> createAssociation() {
        return new Association<InfluxDBPersistentProperty>(this, null);
    }
}
