[![Build Status](https://travis-ci.org/miwurster/spring-data-influxdb.svg?branch=master)](https://travis-ci.org/miwurster/spring-data-influxdb)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3655132cf1784740ac283db42470d8f9)](https://www.codacy.com/app/miwurster/spring-data-influxdb?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=miwurster/spring-data-influxdb&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.miwurster/spring-data-influxdb/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.miwurster/spring-data-influxdb)
[![](https://jitpack.io/v/miwurster/spring-data-influxdb.svg)](https://jitpack.io/#miwurster/spring-data-influxdb)

Spring Data InfluxDB
--------------------

[TOC]

The primary goal of the [Spring Data](http://projects.spring.io/spring-data/) project is to make it easier to build
Spring-powered applications that use new data access technologies such as non-relational databases, map-reduce
frameworks, and cloud based data services.

This modules provides integration with the [InfluxDB](https://influxdata.com/) database and wraps the capabilities of
the official [influxdb-java](https://github.com/influxdata/influxdb-java) library.

## Artifacts

### Maven

```xml
<dependency>
  <groupId>com.github.miwurster</groupId>
  <artifactId>spring-data-influxdb</artifactId>
  <version>1.8</version>
</dependency>
```

## Usage (Spring Boot)

* Following properties can be used in your `application.yml`:

    ```yml
    spring:
      influxdb:
        url: http://localhost:8086
        username: user
        password: ~
        database: test
        retention-policy: autogen
    ```

  Optionally, you can also configure connections, read, and write timeouts (in seconds):

    ```yml
    spring:
      influxdb:    	
        connect-timeout: 10
        read-timeout: 30
        write-timeout: 10
    ```

  Furthermore, one can enable gzip compression in order to reduce size of the transferred data:

    ```yml
    spring:
      influxdb:    	
        gzip: true
    ```

* Create `InfluxDBConnectionFactory` and `InfluxDBTemplate` beans:

    ```java
    @Configuration
    @EnableConfigurationProperties(InfluxDBProperties.class)
    public class InfluxDBConfiguration
    {
      @Bean
      public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties)
      {
        return new InfluxDBConnectionFactory(properties);
      }

      @Bean
      public InfluxDBTemplate<Point> influxDBTemplate(final InfluxDBConnectionFactory connectionFactory)
      {
        /*
         * You can use your own 'PointCollectionConverter' implementation, e.g. in case
         * you want to use your own custom measurement object.
         */
        return new InfluxDBTemplate<>(connectionFactory, new PointConverter());
      }
      
      @Bean
      public DefaultInfluxDBTemplate defaultTemplate(final InfluxDBConnectionFactory connectionFactory)
      {
        /*
         * If you are just dealing with Point objects from 'influxdb-java' you could
         * also use an instance of class DefaultInfluxDBTemplate.
         */
        return new DefaultInfluxDBTemplate(connectionFactory);
      }
    }
    ```

* Use `InfluxDBTemplate` to interact with the InfluxDB database:

    ```java
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    influxDBTemplate.createDatabase();
    final Point p = Point.measurement("disk")
      .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
      .tag("tenant", "default")
      .addField("used", 80L)
      .addField("free", 1L)
      .build();
    influxDBTemplate.write(p);
    ```

## Building

Spring Data InfluxDB uses Maven as its build system.

```bash
mvn clean install
```


## Key Concepts

### database
A logical container for users, retention policies, continuous queries, and time series data.

### field key
The key part of the key-value pair that makes up a field. Field keys are strings and they store metadata.

### field set
The collection of field keys and field values on a point.

### field value
The value part of the key-value pair that makes up a field. Field values are the actual data; they can be strings, floats, integers, or booleans. A field value is always associated with a timestamp.
Field values are not indexed - queries on field values scan all points that match the specified time range and, as a result, are not performant.
Query tip: Compare field values to tag values; tag values are indexed.

### measurement
The part of the InfluxDB data structure that describes the data stored in the associated fields. Measurements are strings.

### point
In InfluxDB, a point represents a single data record, similar to a row in a SQL database table. Each point:

has a measurement, a tag set, a field key, a field value, and a timestamp;
is uniquely identified by its series and timestamp.
You cannot store more than one point with the same timestamp in a series. If you write a point to a series with a timestamp that matches an existing point, the field set becomes a union of the old and new field set, and any ties go to the new field set. For more information about duplicate points, see How does InfluxDB handle duplicate points?

Related entries: field set, series, timestamp