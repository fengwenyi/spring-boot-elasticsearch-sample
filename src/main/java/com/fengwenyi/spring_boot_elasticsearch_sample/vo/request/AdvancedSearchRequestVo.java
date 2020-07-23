package com.fengwenyi.spring_boot_elasticsearch_sample.vo.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 高级搜索 请求VO
 * @author Erwin Feng
 * @since 2020/7/22
 */
@Data
public class AdvancedSearchRequestVo {

    /* 名称 */
    private String name;

    /* 推广广告 */
    private String ad;

    /* 售价:最低 */
    private BigDecimal priceMin;

    /* 售价:最高 */
    private BigDecimal priceMax;

    /* 内存 */
    private String memory;

    /* 存储 */
    private String storage;

    /* 屏幕 */
    private String screen;

    /* 开始时间字符串 */
    private Long startTimeStamp;

    /* 结束时间字符串 */
    private Long endTimeStamp;

    /** 当前页 */
    private Integer currentPage;

}
