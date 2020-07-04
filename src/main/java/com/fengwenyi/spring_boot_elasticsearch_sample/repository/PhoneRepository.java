package com.fengwenyi.spring_boot_elasticsearch_sample.repository;

import com.fengwenyi.spring_boot_elasticsearch_sample.entity.PhoneEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Erwin Feng
 * @since 2020/7/4
 */
@Repository
public interface PhoneRepository extends ElasticsearchRepository<PhoneEntity, String> {
}
