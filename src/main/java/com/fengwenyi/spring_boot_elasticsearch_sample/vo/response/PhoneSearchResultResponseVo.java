package com.fengwenyi.spring_boot_elasticsearch_sample.vo.response;

import com.fengwenyi.api.result.CommonPage;
import com.fengwenyi.spring_boot_elasticsearch_sample.entity.PhoneEntity;

import java.util.List;

/**
 * @author Erwin Feng
 * @since 2020/8/24
 */
public class PhoneSearchResultResponseVo extends CommonPage<List<PhoneEntity>> {

    /** 执行耗时 */
    private String execSpendTime;

    public PhoneSearchResultResponseVo() {
    }

    public PhoneSearchResultResponseVo(Long currentPage, Integer pageSize, Long totalElements, Long totalPages, List<PhoneEntity> content, String execSpendTime) {
        super(currentPage, pageSize, totalElements, totalPages, content);
        this.execSpendTime = execSpendTime;
    }

    public String getExecSpendTime() {
        return execSpendTime;
    }

    public void setExecSpendTime(String execSpendTime) {
        this.execSpendTime = execSpendTime;
    }
}
