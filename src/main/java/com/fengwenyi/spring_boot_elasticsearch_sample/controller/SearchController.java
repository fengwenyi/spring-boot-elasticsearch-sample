package com.fengwenyi.spring_boot_elasticsearch_sample.controller;

import com.fengwenyi.api.result.CommonResponse;
import com.fengwenyi.spring_boot_elasticsearch_sample.service.SearchService;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.AdvancedSearchRequestVo;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.request.FullSearchRequestVo;
import com.fengwenyi.spring_boot_elasticsearch_sample.vo.response.PhoneSearchResultResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 搜索接口
 * @author Erwin Feng
 * @since 2020/7/4
 */
@CrossOrigin
@Api("搜索服务接口")
@RestController
@RequestMapping(value = "/api/search",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

    @Autowired
    private SearchService searchService;

    // 全局搜索
    @ApiOperation("全局搜索接口")
    @PostMapping("/full")
    public Mono<CommonResponse<PhoneSearchResultResponseVo>> fullSearch(@RequestBody FullSearchRequestVo requestVo) {
        CommonResponse<PhoneSearchResultResponseVo> responseEntity = searchService.fullSearch(requestVo);
        return Mono.just(responseEntity);
    }

    // 高级搜索
    @ApiOperation("高级搜索接口")
    @PostMapping("/advanced")
    public Mono<CommonResponse<PhoneSearchResultResponseVo>> advancedSearch(@RequestBody AdvancedSearchRequestVo requestVo) {
        CommonResponse<PhoneSearchResultResponseVo> responseEntity = searchService.advancedSearch(requestVo);
        return Mono.just(responseEntity);
    }

}
