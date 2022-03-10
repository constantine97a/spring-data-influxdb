package org.springframework.data.influxdb.junit.jupiter;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SpringDataInfluxDBExtension implements BeforeAllCallback, ParameterResolver, ContextCustomizerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringDataInfluxDBExtension.class);

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
            .create(SpringDataInfluxDBExtension.class.getName());


    private static final String STORE_KEY_INFLUXDB_CONNECTION = InfluxDBConnection.class.getSimpleName();

    private static final String STORE_KEY_INFLUXDB_CONNECTION_INFO = InfluxDBConnectionInfo.class.getSimpleName();

    private static final Lock initLock = new ReentrantLock();

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        initLock.lock();
        try {
            ExtensionContext.Store store = getStore(extensionContext);
            InfluxDBConnection influxDBConnection = store.getOrComputeIfAbsent(STORE_KEY_INFLUXDB_CONNECTION, key -> {
                LOGGER.debug("creating InfluxDBConnection...");
                return createInfluxDBConnection();
            }, InfluxDBConnection.class);
            store.getOrComputeIfAbsent(STORE_KEY_INFLUXDB_CONNECTION_INFO, key -> influxDBConnection.getInfluxDBConnectionInfo());
        } finally {
            initLock.unlock();
        }
    }

    protected InfluxDBConnection createInfluxDBConnection() {
        return new InfluxDBConnection();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> parameterType = parameterContext.getParameter().getType();
        return parameterType.isAssignableFrom(InfluxDBConnectionInfo.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getStore(extensionContext).get(STORE_KEY_INFLUXDB_CONNECTION_INFO, InfluxDBConnectionInfo.class);
    }

    @Override
    public ContextCustomizer createContextCustomizer(Class<?> aClass, List<ContextConfigurationAttributes> list) {
        return this::customizeContext;
    }

    private void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
        InfluxDBConnectionInfo influxDBConnectionInfo = InfluxDBConnection.influxDBConnectionInfo();
        if (influxDBConnectionInfo != null) {
            context.getBeanFactory().registerResolvableDependency(InfluxDBConnectionInfo.class, influxDBConnectionInfo);
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext extensionContext) {
        return extensionContext.getRoot().getStore(NAMESPACE);
    }

}
