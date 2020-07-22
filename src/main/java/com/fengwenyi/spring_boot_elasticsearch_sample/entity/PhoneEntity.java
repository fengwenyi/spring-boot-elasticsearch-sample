package com.fengwenyi.spring_boot_elasticsearch_sample.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
//@ApiModel("手机实体类")
@Data
@Accessors(chain = true)
@Document(indexName = "spring-boot-elasticsearch-sample-phone")
public class PhoneEntity implements Serializable {

    /* ID */
    @ApiModelProperty("ID")
    @Id
    private String id;

    /* 名称 */
    @ApiModelProperty("名称")
    private String name;

    /* 推广广告 */
    @ApiModelProperty("推广广告")
    private String ad;

    /* 售价 */
    @ApiModelProperty("售价")
    private BigDecimal price;

    /* 图片URL */
    @ApiModelProperty("图片URL")
    private String imgUrl;

    /* 内存 */
    @ApiModelProperty("内存")
    private String memory;

    /* 存储 */
    @ApiModelProperty("存储")
    private String storage;

    /* 屏幕 */
    @ApiModelProperty("屏幕")
    private String screen;


    /* 创建时间，时间戳，毫秒 */
    @ApiModelProperty("创建时间戳（毫秒）")
    private Long createTimeStamp;

    /* 创建时间，yyyy-MM-dd HH:mm:ss,SSS */
    @ApiModelProperty("创建时间字符串")
    private String createTimeString;

}
