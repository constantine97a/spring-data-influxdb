package org.springframework.data.influxdb.junit.jupiter;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.testcontainers.containers.InfluxDBContainer;

/**
 * The information about {@link InfluxDBConnection}
 * The {@link #host},{@link #httpPort} {@link #transportPort} values specify the values needed to connect to the cluster with reset client for both a local started influxdb
 * and for one defined by url when create the {@link InfluxDBConnection}
 * The object must be created by using a {@link  Builder}
 *
 * @author Yuri Yin
 */
public class InfluxDBConnectionInfo {

    private final IntegrationtestEnvironment integrationTestEnvironment;

    private final boolean useSsl;

    private final String host;

    private final int httpPort;

    private final String databaseName;


    @Nullable
    private final InfluxDBContainer influxDBContainer;


    private InfluxDBConnectionInfo(IntegrationtestEnvironment integrationtestEnvironment,
                                   boolean useSsl,
                                   String host,
                                   int httpPort,
                                   String databaseName,
                                   @Nullable InfluxDBContainer influxDBContainer) {
        this.integrationTestEnvironment = integrationtestEnvironment;
        this.useSsl = useSsl;
        this.host = host;
        this.httpPort = httpPort;
        this.databaseName = databaseName;
        this.influxDBContainer = influxDBContainer;
    }

    public IntegrationtestEnvironment getIntegrationTestEnvironment() {
        return integrationTestEnvironment;
    }

    public boolean isUseSsl() {
        return useSsl;
    }

    public String getHost() {
        return host;
    }

    public int getHttpPort() {
        return httpPort;
    }


    public String getDatabaseName() {
        return databaseName;
    }

    @Nullable
    public InfluxDBContainer getInfluxDBContainer() {
        return influxDBContainer;
    }

    @Override
    public String toString() {
        return "InfluxDBConnectionInfo{" +
                "configuration=" + integrationTestEnvironment +
                ", useSsl=" + useSsl +
                ", host='" + host + '\'' +
                ", httpPort=" + httpPort +
                ", databaseName='" + databaseName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        private IntegrationtestEnvironment integrationTestEnvironment;

        private boolean useSsl;

        private String host;

        private int httpPort;

        private int transportPort;

        private String databaseName;

        @Nullable
        private InfluxDBContainer influxDBContainer;


        public Builder withIntegrationTestEnvironment(IntegrationtestEnvironment integrationTestEnvironment) {
            this.integrationTestEnvironment = integrationTestEnvironment;
            return this;
        }

        public Builder withHostAndPort(String host, int httpPort) {
            Assert.hasLength(host, "host must not be empty");
            Assert.isTrue(httpPort > 256, "http Port should larger than reserved port");
            this.host = host;
            this.httpPort = httpPort;
            return this;
        }

        public Builder useSSL(boolean useSsl) {
            this.useSsl = useSsl;
            return this;
        }


        public Builder withDatabaseName(String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        public Builder withInfluxDBContainer(InfluxDBContainer influxDBContainer) {
            this.influxDBContainer = influxDBContainer;
            return this;
        }

        public InfluxDBConnectionInfo build() {
            return new InfluxDBConnectionInfo(this.integrationTestEnvironment, this.useSsl, this.host, this.httpPort,
                    this.databaseName, this.influxDBContainer);
        }


    }
}

