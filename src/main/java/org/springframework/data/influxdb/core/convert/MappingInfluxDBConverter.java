package org.springframework.data.influxdb.core.convert;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.influxdb.annotation.Point;
import org.springframework.data.influxdb.core.mapping.InfluxDBPersistentEntity;
import org.springframework.data.influxdb.core.mapping.InfluxDBPersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class MappingInfluxDBConverter implements InfluxDBConverter, ApplicationContextAware, InitializingBean {

    private final MappingContext<? extends InfluxDBPersistentEntity<?>, InfluxDBPersistentProperty> mappingContext;

    private final GenericConversionService conversionService;

    public MappingInfluxDBConverter(MappingContext<? extends InfluxDBPersistentEntity<?>, InfluxDBPersistentProperty> mappingContext) {
        this(mappingContext, null);
    }

    public MappingInfluxDBConverter(MappingContext<? extends InfluxDBPersistentEntity<?>, InfluxDBPersistentProperty> mappingContext,
                                    @Nullable GenericConversionService conversionService) {
        Assert.notNull(mappingContext, "MappingContext must not be null!");

        this.mappingContext = mappingContext;
        this.conversionService = conversionService;
    }

    @Override
    public MappingContext<? extends InfluxDBPersistentEntity<?>, InfluxDBPersistentProperty> getMappingContext() {
        return null;
    }

    @Override
    public ConversionService getConversionService() {
        return null;
    }

    @Override
    public <R> R read(Class<R> type, Point source) {
        return null;
    }

    @Override
    public void write(Object source, Point sink) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
