package org.springframework.data.influxdb.junit;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.influxdb.junit.jupiter.SpringIntegrationTest;


@Slf4j
@SpringIntegrationTest
public class InfluxDBContainerStartUpTest {

    @Test
    public void startUpInfluxDB() {
        System.out.println("startUpInfluxDB startUp");
    }
}
