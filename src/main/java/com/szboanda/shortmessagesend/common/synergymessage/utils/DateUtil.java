package com.szboanda.shortmessagesend.common.synergymessage.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * 主数据处理器辅助工具
 * 
 * @author zhangsheng
 *
 */
public final class DateUtil {

    /**
     * 日期的格式，带时分秒
     */
    public static final String DATE_PATTERN_HMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期的格式，不带时分秒
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";







    /**
     * 根据有无修改时间进行分类
     * 
     * @param srcData
     * @param hasModifyData
     * @param emptyModfyData
     */
    public static void modifyTimeGroup(List<Map<String, Object>> srcData,
            List<Map<String, Object>> hasModifyData, List<Map<String, Object>> emptyModfyData) {
        if (null == hasModifyData) {
            hasModifyData = new ArrayList<Map<String, Object>>();
        }
        if (null == emptyModfyData) {
            emptyModfyData = new ArrayList<Map<String, Object>>();
        }
        if (null != srcData && !srcData.isEmpty()) {
            for (int i = 0; i < srcData.size(); i++) {
                if (null != srcData.get(i).get("XGSJ")) {
                    hasModifyData.add(srcData.get(i));
                } else {
                    emptyModfyData.add(srcData.get(i));
                }
            }
        }
    }



}
