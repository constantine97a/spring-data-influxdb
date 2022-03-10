package org.springframework.data.influxdb.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * InfluxDBNamespaceHandler
 *
 * @author Yuri Yin
 */
public class InfluxDBNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        RepositoryConfigurationExtension extension = new InfluxDBRepositoryConfigExtension();
        RepositoryBeanDefinitionParser parser = new RepositoryBeanDefinitionParser(extension);

        registerBeanDefinitionParser("repositories", parser);
        registerBeanDefinitionParser("influxdb", new RestClientBeanDefinitionParser());
    }
}
