package com.fengwenyi.spring_boot_elasticsearch_sample.service;

import com.fengwenyi.api.result.CommonResponse;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.AdvancedSearchRequestVo;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.FullSearchRequestVo;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.response.PhoneSearchResultResponseVo;

/**
 * 搜索服务接口
 * @author Erwin Feng
 * @since 2020/7/11
 */
public interface SearchService {

    /**
     * 全文搜索
     * @param requestVo {@link FullSearchRequestVo}
     * @return
     */
    CommonResponse<PhoneSearchResultResponseVo> fullSearch(FullSearchRequestVo requestVo);

    /**
     * 高级搜索
     * @param requestVo 搜索条件
     * @return 返回搜索结果
     */
    CommonResponse<PhoneSearchResultResponseVo> advancedSearch(AdvancedSearchRequestVo requestVo);
}
