package com.fengwenyi.spring_boot_elasticsearch_sample.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    /* 卖点，多个用（||）隔开 */
    private String sellingPoints;

    /* 售价 */
    private String price;

    /* 品牌 */
    private String brand;

    /* 产量 */
    private Long yield;

    /* 销售量 */
    private Long sale;

    /* 颜色，用英文分号(||)分隔 */
    private String colors;

    /* 上市时间 */
    private LocalDateTime marketTime;

    /* 创建时间 */
    private Long createTimeStamp;
    private String createTimeString;

}
