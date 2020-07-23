package com.fengwenyi.spring_boot_elasticsearch_sample.service.impl;

import com.fengwenyi.api_result.entity.ResponseEntity;
import com.fengwenyi.api_result.util.ResponseUtils;
import com.fengwenyi.javalib.util.StringUtils;
import com.fengwenyi.spring_boot_elasticsearch_sample.entity.PhoneEntity;
import com.fengwenyi.spring_boot_elasticsearch_sample.service.SearchService;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.AdvancedSearchRequestVo;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.FullSearchRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /** 默认分页大小 */
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public ResponseEntity<Void, Map<String, Object>> fullSearch(FullSearchRequestVo requestVo) {

        Integer currentPage = requestVo.getCurrentPage();
        String keyword = requestVo.getKeyword();

        // 构造分页类
        Pageable pageable = this.getPageable(currentPage);

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
        long searchStartTime = System.nanoTime();
        SearchHits<PhoneEntity> searchHits = elasticsearchRestTemplate.search(searchQuery, PhoneEntity.class);
        long searchEndTime = System.nanoTime();
//        searchHits.get().peek(
//                phoneEntitySearchHit -> {
//                    log.info("id: {}", phoneEntitySearchHit.getId());
//                    Map<String, List<String>> highlightFields = phoneEntitySearchHit.getHighlightFields();
//                    for (Map.Entry<String, List<String>> entry : highlightFields.entrySet()) {
//                        log.info("getHighlightFields key={}", entry.getKey());
//                        List<String> values = entry.getValue();
//                        for (String value : values) {
//                            log.info("getHighlightFields value={}", value);
//                        }
//                    }
//                    log.info("getHighlightFields: {}", phoneEntitySearchHit.getHighlightFields());
//                    log.info("getScore: {}", phoneEntitySearchHit.getScore());
//                    log.info("getSortValues: {}", phoneEntitySearchHit.getSortValues());
//                    log.info(phoneEntitySearchHit.getContent().toString());
//                }).collect(Collectors.toList());

        float maxScore = searchHits.getMaxScore();
//        log.info("maxScore={}", maxScore);

        long totalHits = searchHits.getTotalHits();
//        log.info("totalHits={}", totalHits);

//        log.info("getTotalHitsRelation: {}", searchHits.getTotalHitsRelation());

        List<PhoneEntity> list = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());

        double searchSpendTime = (double) (searchEndTime - searchStartTime) / 1000000000;

        Map<String, Object> map = new HashMap<>();
        map.put("time", searchSpendTime);
        map.put("content", list);

        return ResponseUtils.success(map, totalHits, this.getTotalPages(totalHits), DEFAULT_PAGE_SIZE, (long) this.dealCurrentPage(requestVo.getCurrentPage()) + 1);
    }

    @Override
    public ResponseEntity<Void, Map<String, Object>> advancedSearch(AdvancedSearchRequestVo requestVo) {

        // NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        NativeSearchQuery searchQuery = this.pacAdvancedSearchQuery(requestVo);

        // page search
        long searchStartTime = System.nanoTime();
        SearchHits<PhoneEntity> searchHits = elasticsearchRestTemplate.search(searchQuery, PhoneEntity.class);
        long searchEndTime = System.nanoTime();

        long totalHits = searchHits.getTotalHits();

        List<PhoneEntity> list = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());

        double searchSpendTime = (double) (searchEndTime - searchStartTime) / 1000000000;

        Map<String, Object> map = new HashMap<>();
        map.put("time", searchSpendTime);
        map.put("content", list);

        return ResponseUtils.success(map, totalHits, this.getTotalPages(totalHits), DEFAULT_PAGE_SIZE, (long) this.dealCurrentPage(requestVo.getCurrentPage()) + 1);
    }

    //-------------------------------- private method start -------------------------------------------------------------------------

    private NativeSearchQuery pacAdvancedSearchQuery(AdvancedSearchRequestVo requestVo) {

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if (!StringUtils.isEmpty(requestVo.getName())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", requestVo.getName()));
        }
        if (!StringUtils.isEmpty(requestVo.getName())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("ad", requestVo.getName()));
        }
        if (requestVo.getPriceMin() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gte(requestVo.getPriceMin()));
        }
        if (requestVo.getPriceMax() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(requestVo.getPriceMax()));
        }
        if (!StringUtils.isEmpty(requestVo.getMemory())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("memory", requestVo.getMemory()));
        }
        if (!StringUtils.isEmpty(requestVo.getStorage())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("storage", requestVo.getStorage()));
        }
        if (!StringUtils.isEmpty(requestVo.getScreen())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("screen", requestVo.getScreen()));
        }
        if (requestVo.getStartTimeStamp() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("createTimeStamp").gte(requestVo.getStartTimeStamp()));
        }
        if (requestVo.getEndTimeStamp() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("createTimeStamp").lte(requestVo.getEndTimeStamp()));
        }

        Pageable pageable = this.getPageable(requestVo.getCurrentPage());

        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(boolQueryBuilder)
                .build();
    }

    /**
     * 处理当前页
     * @param currentPage 当前页
     * @return 返回当前页
     */
    private int dealCurrentPage(Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1; // if page is null or page < 0, page = 0
        }
        return currentPage - 1;
    }

    /**
     * 获取分页信息
     * @param currentPage 当前页
     * @return
     */
    private Pageable getPageable(Integer currentPage) {
        currentPage = this.dealCurrentPage(currentPage);
        return PageRequest.of(currentPage, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取总页数
     * @param totalRecords 总记录数
     * @return 总页数
     */
    private long getTotalPages(long totalRecords) {
        long quotient = totalRecords / DEFAULT_PAGE_SIZE;
        long mod = totalRecords % DEFAULT_PAGE_SIZE;
        if (mod > 0) {
            return quotient + 1;
        }
        return quotient;
    }


    //-------------------------------- private method end -------------------------------------------------------------------------

}
