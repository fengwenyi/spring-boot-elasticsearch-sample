package com.fengwenyi.spring_boot_elasticsearch_sample.service;

import com.fengwenyi.api_result.entity.ResponseEntity;
import com.fengwenyi.api_result.model.ResultModel;
import com.fengwenyi.spring_boot_elasticsearch_sample.entity.PhoneEntity;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.AdvancedSearchRequestVo;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.FullSearchRequestVo;

import java.util.List;
import java.util.Map;

/**
 * 搜索服务接口
 * @author Erwin Feng
 * @since 2020/7/11
 */
public interface SearchService {

    /**
     * 全文搜索
     * @param requestVo {@link FullSearchRequestVo}
     * @return {@link ResponseEntity}
     */
    ResponseEntity<Void, Map<String, Object>> fullSearch(FullSearchRequestVo requestVo);

    /**
     * 高级搜索
     * @param requestVo 搜索条件
     * @return 返回搜索结果
     */
    ResponseEntity<Void, Map<String, Object>> advancedSearch(AdvancedSearchRequestVo requestVo);
}
