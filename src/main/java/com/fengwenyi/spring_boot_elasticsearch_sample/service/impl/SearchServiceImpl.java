package com.fengwenyi.spring_boot_elasticsearch_sample.service.impl;

import com.fengwenyi.api_result.model.ResultModel;
import com.fengwenyi.javalib.util.StringUtils;
import com.fengwenyi.spring_boot_elasticsearch_sample.entity.PhoneEntity;
import com.fengwenyi.spring_boot_elasticsearch_sample.service.SearchService;
import com.fengwenyi.spring_boot_elasticsearch_sample.util.ResponseUtils;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.FullSearchRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 搜索服务接口实现类
 *
 * @author Erwin Feng
 * @since 2020/7/11
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public ResultModel fullSearch(FullSearchRequestVo requestVo) {

        Integer currentPage = requestVo.getCurrentPage();
        String keyword = requestVo.getKeyword();

        // 校验参数
        if (currentPage == null || currentPage < 1)
            currentPage = 1; // if page is null, page = 0

        // 构造分页类
        Pageable pageable = PageRequest.of(currentPage, 10);

    /*
    SearchQuery
    这个很关键，这是搜索条件的入口，
    elasticsearchTemplate 会 使用它 进行搜索
     */

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withPageable(pageable);

        if (!StringUtils.isEmpty(keyword)) {
            // keyword must not null
            nativeSearchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keyword));
        }

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();

        // page search
        SearchHits<PhoneEntity> searchHits = elasticsearchRestTemplate.search(searchQuery, PhoneEntity.class);

        searchHits.get().peek(phoneEntitySearchHit -> log.info(phoneEntitySearchHit.getContent().toString())).collect(Collectors.toList());

        float maxScore = searchHits.getMaxScore();
        log.info("maxScore={}", maxScore);

        long totalHits = searchHits.getTotalHits();
        log.info("totalHits={}", totalHits);

        return ResponseUtils.success();
    }
}
