package org.springframework.data.influxdb.junit.jupiter;

import java.util.Locale;

/**
 * @author yuri.yin
 */
public enum IntegrationtestEnvironment {
    INFLUXDB, UNDEFINED;

    public static final String SYSTEM_PROPERTY = "sde.integration-test.environment";

    public static IntegrationtestEnvironment get() {
        String property = System.getProperty(SYSTEM_PROPERTY, "influxdb");

        switch (property.toUpperCase(Locale.getDefault())) {
        case "INFLUXDB":
            return INFLUXDB;
        default:
            return UNDEFINED;
        }
    }

}
