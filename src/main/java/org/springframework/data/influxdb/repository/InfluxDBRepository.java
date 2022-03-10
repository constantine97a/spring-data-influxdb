package org.springframework.data.influxdb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;

/**
 * @param <T>         entity
 * @param <TIMESTAMP> timestamp for measurement
 * @author yuri.yin
 */
@NoRepositoryBean
public interface InfluxDBRepository<T, TIMESTAMP> extends PagingAndSortingRepository<T, TIMESTAMP> {

    /**
     * search for entity using a marellike query
     *
     * @param entity   the entity for which measurement should be searched ,must not be {@literal null}
     * @param fields   the field name
     * @param pageable must not be {@literal null}
     * @return
     */
    Page<T> search(T entity, @Nullable String[] fields, Pageable pageable);
}
