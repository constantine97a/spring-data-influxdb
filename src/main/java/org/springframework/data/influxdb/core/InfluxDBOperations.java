/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.influxdb.core;

import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.data.convert.EntityConverter;
import org.springframework.data.influxdb.core.convert.InfluxDBConverter;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


/**
 * InfluxDBOperations. Since 4.0 this interface only contains common helper functions, the other methods have been
 * moved to the different interfaces that are extended by ElasticsearchOperations. The interfaces now reflect the
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/rest-apis.html">REST API structure</a> of
 * Elasticsearch.
 *
 * @author Rizwan Idrees
 * @author Mohsin Husen
 * @author Kevin Leturc
 * @author Zetang Zeng
 * @author Dmitriy Yakovlev
 * @author Peter-Josef Meisch
 */
public interface InfluxDBOperations<T> {

    /**
     * Ensures that the configured database exists.
     */
    void createDatabase();

    /**
     * Write a single measurement to the database.
     *
     * @param payload the measurement to write to
     */
    @SuppressWarnings("unchecked")
    void write(final T... payload);

    /**
     * Write a set of measurements to the database.
     *
     * @param payload the values to write to
     */
    void write(final List<T> payload);

    /**
     * Executes a query against the database.
     *
     * @param query the query to execute
     * @return a List of time series data matching the query
     */
    QueryResult query(final Query query);

    /**
     * Executes a query against the database.
     *
     * @param query    the query to execute
     * @param timeUnit the time unit to be used for the query
     * @return a List of time series data matching the query
     */
    QueryResult query(final Query query, final TimeUnit timeUnit);

    /**
     * Execute a streaming query against the database.
     *
     * @param query     the query to execute
     * @param chunkSize the number of QueryResults to process in one chunk
     * @param consumer  the consumer to invoke for each received QueryResult
     */
    void query(final Query query, final int chunkSize, final Consumer<QueryResult> consumer);

    /**
     * Ping the database.
     *
     * @return the response of the ping execution
     */
    Pong ping();

    /**
     * Return the version of the connected database.
     *
     * @return the version String, otherwise unknown
     */
    String version();


    /**
     * @return the {@link EntityConverter} influxdb implementation
     */
    InfluxDBConverter getInfluxDBConverter();


}
