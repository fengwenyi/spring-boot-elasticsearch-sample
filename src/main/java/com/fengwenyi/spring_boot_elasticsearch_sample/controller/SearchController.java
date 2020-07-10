package com.fengwenyi.spring_boot_elasticsearch_sample.controller;

import com.fengwenyi.api_result.model.ResultModel;
import com.fengwenyi.spring_boot_elasticsearch_sample.service.SearchService;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.FullSearchRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 搜索接口
 * @author Erwin Feng
 * @since 2020/7/4
 */
@RestController
@RequestMapping(value = "/api/search",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

    @Autowired
    private SearchService searchService;

    // 全局搜索
    @PostMapping("/full")
    public Mono<ResultModel> fullSearch(@RequestBody FullSearchRequestVo requestVo) {
        ResultModel responseEntity = searchService.fullSearch(requestVo);
        return Mono.just(responseEntity);
    }

    // 高级搜索

}
