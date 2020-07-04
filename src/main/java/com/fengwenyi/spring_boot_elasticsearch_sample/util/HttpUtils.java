package com.fengwenyi.spring_boot_elasticsearch_sample.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * Http工具类
 * @author Erwin Feng
 * @since 2020/7/5
 */
public class HttpUtils {

    /**
     * http get请求
     * @param url
     * @return
     */
    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
