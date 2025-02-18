package org.springframework.data.influxdb.junit.jupiter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(SpringDataInfluxDBExtension.class)
@Tag(Tags.INTEGRATION_TEST)
public @interface IntegrationTest {

}
