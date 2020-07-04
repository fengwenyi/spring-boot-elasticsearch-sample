package com.fengwenyi.spring_boot_elasticsearch_sample.util;

import com.fengwenyi.api_result.helper.ResultHelper;
import com.fengwenyi.api_result.model.ResultModel;
import com.fengwenyi.spring_boot_elasticsearch_sample.constant.Constants;

/**
 * 响应工具类
 * @author Erwin Feng
 * @since 2020/7/4
 */
public class ResponseUtils {

    /**
     * 成功
     * @return {@link ResultModel}
     */
    public static ResultModel<Void> success() {
        return ResultHelper.success(Constants.RESPONSE_DEFAULT_SUCCESS_MESSAGE);
    }

}
