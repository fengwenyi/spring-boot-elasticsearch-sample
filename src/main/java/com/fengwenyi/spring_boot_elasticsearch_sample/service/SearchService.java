package com.fengwenyi.spring_boot_elasticsearch_sample.service;

import com.fengwenyi.api_result.model.ResultModel;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.FullSearchRequestVo;

/**
 * 搜索服务接口
 * @author Erwin Feng
 * @since 2020/7/11
 */
public interface SearchService {

    /**
     * 全文搜索
     * @param requestVo {@link FullSearchRequestVo}
     * @return {@link ResultModel}
     */
    ResultModel fullSearch(FullSearchRequestVo requestVo);
}
