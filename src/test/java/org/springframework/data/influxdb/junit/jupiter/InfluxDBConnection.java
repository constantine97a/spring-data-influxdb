package org.springframework.data.influxdb.junit.jupiter;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class InfluxDBConnection implements ExtensionContext.Store.CloseableResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfluxDBConnection.class);
    private static final String SDE_TESTCONTAINER_IMAGE_NAME = "sde.testcontainers.image-name";
    private static final String SDE_TESTCONTAINER_IMAGE_VERSION = "sde.testcontainers.image-version";


    private static final String DOCKER_INFLUXDB_INIT_MODE = "DOCKER_INFLUXDB_INIT_MODE";

    private static final String DOCKER_INFLUXDB_INIT_USERNAME = "DOCKER_INFLUXDB_INIT_USERNAME";

    private static final String DOCKER_INFLUXDB_INIT_PASSWORD = "DOCKER_INFLUXDB_INIT_PASSWORD";

    private static final String DOCKER_INFLUXDB_INIT_ORG = "DOCKER_INFLUXDB_INIT_ORG";

    private static final String DOCKER_INFLUXDB_INIT_BUCKET = "DOCKER_INFLUXDB_INIT_BUCKET";

    private static final String DOCKER_INFLUXDB_INIT_ADMIN_TOKEN = "DOCKER_INFLUXDB_INIT_ADMIN_TOKEN";

    /**
     * default port
     */
    private static final int INFLUXDB_DEFAULT_PORT = 8086;

    private static final ThreadLocal<InfluxDBConnectionInfo> clusterConnectionInfoThreadLocal = new ThreadLocal<>();


    @Nullable
    private final InfluxDBConnectionInfo influxDBConnectionInfo;

    public InfluxDBConnection() {
        influxDBConnectionInfo = startInfluxDBContainer();
        if (this.influxDBConnectionInfo != null) {
            LOGGER.debug(influxDBConnectionInfo.toString());
            clusterConnectionInfoThreadLocal.set(influxDBConnectionInfo);

        } else {
            LOGGER.error("could not create clusterConnectionInfo");
        }
    }

    public static InfluxDBConnectionInfo influxDBConnectionInfo() {
        return clusterConnectionInfoThreadLocal.get();
    }


    public InfluxDBConnectionInfo getInfluxDBConnectionInfo() {
        return influxDBConnectionInfo;
    }

    private InfluxDBConnectionInfo startInfluxDBContainer() {
        LOGGER.info("Starting InfluxDB Container.....");
        try {
            IntegrationtestEnvironment integrationtestEnvironment = IntegrationtestEnvironment.get();
            LOGGER.info("Integration test environment: {}", integrationtestEnvironment);
            if (integrationtestEnvironment == IntegrationtestEnvironment.UNDEFINED) {
                throw new IllegalArgumentException(IntegrationtestEnvironment.SYSTEM_PROPERTY + " property not set");
            }
            String testcontainersConfiguration = integrationtestEnvironment.name().toLowerCase();
            Map<String, String> testcontainersProperties = testcontainersProperties("testcontainers-" + testcontainersConfiguration + ".properties");
            DockerImageName dockerImageName = this.getDockerImageName(testcontainersProperties);
            InfluxDBContainer influxDBContainer = new InfluxDBContainer<>(dockerImageName).withEnv(testcontainersProperties);
            influxDBContainer.start();

            return InfluxDBConnectionInfo.builder()
                    .withIntegrationTestEnvironment(integrationtestEnvironment)
                    .withInfluxDBContainer(influxDBContainer)
                    .withHostAndPort(
                            influxDBContainer.getHost(),
                            influxDBContainer.getMappedPort(INFLUXDB_DEFAULT_PORT))
                    .withDatabaseName(testcontainersProperties.get(DOCKER_INFLUXDB_INIT_BUCKET)).build();
        } catch (Exception e) {
            LOGGER.error("Can not start influxdb container", e);
        }
        return null;
    }


    private DockerImageName getDockerImageName(Map<String, String> testcontainersProperties) {
        Assert.notNull(testcontainersProperties, "testcontainersProperties is none");

        String imageName = testcontainersProperties.get(SDE_TESTCONTAINER_IMAGE_NAME);
        String imageVersion = testcontainersProperties.get(SDE_TESTCONTAINER_IMAGE_VERSION);
        if (imageName == null || imageName.length() == 0) {
            throw new IllegalArgumentException("property " + SDE_TESTCONTAINER_IMAGE_NAME + " not configured");
        }
        testcontainersProperties.remove(SDE_TESTCONTAINER_IMAGE_NAME);

        if (imageVersion == null || imageVersion.length() == 0) {
            throw new IllegalArgumentException("property " + SDE_TESTCONTAINER_IMAGE_VERSION + " not configured");
        }
        testcontainersProperties.remove(SDE_TESTCONTAINER_IMAGE_VERSION);
        String configuredImageName = String.format("%s:%s", imageName, imageVersion);
        DockerImageName dockerImageName = DockerImageName.parse(configuredImageName);
        LOGGER.info("Docker image: {}", dockerImageName);
        return dockerImageName;
    }

    private Map<String, String> testcontainersProperties(String propertiesFile) {
        LOGGER.info("load configuration from {}", propertiesFile);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            Properties props = new Properties();

            if (inputStream != null) {
                props.load(inputStream);
            }
            Map<String, String> influxdbProperties = new LinkedHashMap<>();
            props.forEach((key, value) -> influxdbProperties.put(key.toString(), value.toString()));
            return influxdbProperties;
        } catch (Exception e) {
            LOGGER.error("Cannot load " + propertiesFile);
        }
        return Collections.emptyMap();
    }

    /**
     * close hook of {@link ExtensionContext.Store.CloseableResource} while ExtensionContext try to deconstruct the InfluxDBConnection
     *
     * @throws Throwable thrown
     */
    @Override
    public void close() throws Throwable {
        if (influxDBConnectionInfo != null && influxDBConnectionInfo.getInfluxDBContainer() != null) {
            LOGGER.info("Stopping container...");
            influxDBConnectionInfo.getInfluxDBContainer().stop();
        }
        LOGGER.info("closed");
    }
}
