package com.fengwenyi.spring_boot_elasticsearch_sample.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

/**
 * @author Erwin Feng
 * @since 2020/7/4
 */
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T coverObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String coverObject(String json) {
        try {
            return objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Collection<T> coverCollection(String json, Class<Collection<T>> collectionClass) {
        try {
            return objectMapper.readValue(json, collectionClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
