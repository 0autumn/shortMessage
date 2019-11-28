/******************************************************************************
* Copyright (C) 2016 ShenZhen Powerdata Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package com.szboanda.shortmessagesend.common.utils;

import org.apache.log4j.Logger;

/**
* @Title:
* @author  zhangsheng
* @since   JDK1.6
* @history 2016年11月28日 zhangsheng 新建
*/
public final class LoggerUtil {

    /**
     * 不使用对象实例
     */
    private LoggerUtil() {}
    
    /**
     * 调试日志
     * @param clazz
     * @param msg
     * @param ex
     */
    @SuppressWarnings("rawtypes")
    public static void debug(Class clazz, String msg, Exception ex) {
        Logger logger = Logger.getLogger(clazz);
        if (logger.isDebugEnabled()) {
            if (null != ex) {
                logger.debug(msg, ex);
            } else {
                logger.debug(msg);
            }
        }
    }
    
    /**
     * 信息日志
     * @param clazz
     * @param msg
     * @param ex
     */
    @SuppressWarnings("rawtypes")
    public static void info(Class clazz, String msg, Exception ex) {
        Logger logger = Logger.getLogger(clazz);
        if (null != ex) {
            logger.info(msg, ex);
        } else {
            logger.info(msg);
        }
    }
    
    /**
     * 错误日志
     * @param clazz
     * @param msg
     * @param ex
     */
    @SuppressWarnings("rawtypes")
    public static void error(Class clazz, String msg) {
        Logger logger = Logger.getLogger(clazz);
        logger.error(msg);
    }
    
    /**
     * 错误日志
     * @param clazz
     * @param msg
     * @param ex
     */
    @SuppressWarnings("rawtypes")
    public static void error(Class clazz, Exception ex) {
        Logger logger = Logger.getLogger(clazz);
        logger.error(ex);
    }
    
    /**
     * 错误日志
     * @param clazz
     * @param msg
     * @param ex
     */
    @SuppressWarnings("rawtypes")
    public static void error(Class clazz, String msg, Exception ex) {
        Logger logger = Logger.getLogger(clazz);
        if (null != ex) {
            logger.error(msg, ex);
        } else {
            logger.error(msg);
        }
    }
}
