/******************************************************************************
* Copyright (C) 2018 ShenZhen Powerdata Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package com.szboanda.shortmessagesend.common.utils;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Title: Json帮助类
 * @author 庞东博
 * @date 2016年5月26日
 * @version V1.0
 */
public abstract class JsonHelper {

    /**
     * 将map对象转换为json格式的字符串
     * 
     * @param data
     * @return
     */
    public static String parseObject(Object data) {
        String jsonString = null;
        if (null != data) {
            try {
                ObjectMapper objectMapper = getObjectMapper();
                objectMapper.setSerializationInclusion(Include.NON_NULL);
                jsonString = objectMapper.writeValueAsString(data);
            } catch (IOException ex) {
                throw new RuntimeException("对象转json异常:" + data, ex);
            }
        }
        return jsonString;
    }

    /**
     * 将json字符串转为map对象
     * 
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonString2Map(String json) {
        Map<String, Object> mapData = null;
        if (StringUtils.isNotEmpty(json)) {
            try {
                ObjectMapper objectMapper = getObjectMapper();
                mapData = objectMapper.readValue(json, Map.class);
            } catch (Exception ex) {
                throw new RuntimeException("Json转换异常 : " + json, ex);
            }
        }
        return mapData;
    }

    /**
     * 将json数组字符串转为List对象
     * 
     * @param json
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> jsonArrayString2List(String json) {
        List<Map<String, Object>> mapData = null;
        if (StringUtils.isNotEmpty(json)) {
            try {
                ObjectMapper objectMapper = getObjectMapper();
                mapData = objectMapper.readValue(json, List.class);
            } catch (Exception ex) {
                throw new RuntimeException("Json转换异常 : " + json, ex);
            }
        }
        return mapData;
    }

    /**
     * 将json字符串转为对象
     * 
     * @param jsonArray
     * @return
     * @throws Exception
     */
    public static Object jsonString2Object(String json, String className) {
        Object mapData = null;
        if (StringUtils.isNotEmpty(json)) {
            try {
                ObjectMapper objectMapper = getObjectMapper();
                mapData = objectMapper.readValue(json, Class.forName(className));
            } catch (Exception ex) {
                throw new RuntimeException("Json转换异常 : " + json, ex);
            }
        }
        return mapData;
    }

    /**
     * 将json字符串转为对象
     * 
     * @param jsonArray
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object jsonString2Object(String json, @SuppressWarnings("rawtypes") Class clazz) {
        Object mapData = null;
        if (StringUtils.isNotEmpty(json)) {
            try {
                ObjectMapper objectMapper = getObjectMapper();
                mapData = objectMapper.readValue(json, clazz);
            } catch (Exception ex) {
                throw new RuntimeException("Json转换异常 : " + json, ex);
            }
        }
        return mapData;
    }
    
    /**
     * 获取ObjectMapper对象
     * 
     * @return
     */
    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 允许出现特殊字符和转义符
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        // 允许出现单引号
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);

        return objectMapper;
    }
}
