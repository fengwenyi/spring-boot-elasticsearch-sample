package com.fengwenyi.spring_boot_elasticsearch_sample.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 * @author Erwin Feng
 * @since 2020/7/22
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config {
}
