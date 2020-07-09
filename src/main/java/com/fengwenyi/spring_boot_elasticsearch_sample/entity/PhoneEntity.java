package com.fengwenyi.spring_boot_elasticsearch_sample.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 手机实体类
 * @author Erwin Feng
 * @since 2020/7/4
 */
@Data
@Accessors(chain = true)
@Document(indexName = "spring-boot-elasticsearch-sample-phone")
public class PhoneEntity implements Serializable {

    /* ID */
    @Id
    private String id;

    /* 名称 */
    private String name;

    /* 推广广告 */
    private String ad;

    /* 售价 */
    private BigDecimal price;

    /* 图片URL */
    private String imgUrl;

    /* 内存 */
    private String memory;

    /* 存储 */
    private String storage;

    /* 屏幕 */
    private String screen;


    /* 创建时间，时间戳，毫秒 */
    private Long createTimeStamp;

    /* 创建时间，yyyy-MM-dd HH:mm:ss,SSS */
    private String createTimeString;

}
