package org.springframework.data.influxdb.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class VersionInfoTest {

    @Test
    @DisplayName("should read version properties")
    void shouldReadVersionProperties() {

        Properties properties = VersionInfo.versionProperties();

        assertThat(properties).isNotNull();
        assertThat(properties.getProperty("version.spring-data-influxdb")).isNotNull();
        assertThat(properties.getProperty("version.influxdb-client")).isNotNull();

    }
}