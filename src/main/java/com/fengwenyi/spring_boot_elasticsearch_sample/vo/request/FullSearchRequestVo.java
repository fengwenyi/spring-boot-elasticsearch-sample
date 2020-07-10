package com.fengwenyi.spring_boot_elasticsearch_sample.vo.request;

import lombok.Data;

/**
 * 全文搜索请求VO
 * @author Erwin Feng
 * @since 2020/7/11
 */
@Data
public class FullSearchRequestVo {

    /** 当前页 */
    private Integer currentPage;

    /** 关键字 */
    private String keyword;

}
