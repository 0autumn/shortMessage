/******************************************************************************
* Copyright (C) 2016 ShenZhen Powerdata Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package com.szboanda.shortmessagesend.common.synergymessage.been;


import com.szboanda.shortmessagesend.common.synergymessage.utils.XmlMapUtil;

import java.util.Map;

/**
* @Title: soap请求实体
* @author  zhangsheng
* @since   JDK1.6
* @history 2016年11月21日 zhangsheng 新建
*/
public class SoapRequest {

    /**
     * webservice地址
     */
    private String wsUrl ;

    /**
     * 方法名
     */
    private String method;
    /**
     * 命名空间
     */
    private String nameSpace;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 构造方法
     */
    public SoapRequest(String wsUrl) {
        this.wsUrl = wsUrl;
    }
    
    /**
     * 有参构造方法
     * @param wsUrl
     * @param method
     * @param contentMap
     * @param nodeNames
     */
    public SoapRequest(String wsUrl, String method, Map<String, Object> contentMap, Map<String, String> nodeNames) {
        this.wsUrl = wsUrl;
        this.method = method;
        this.content = XmlMapUtil.map2xml(contentMap, XmlMapUtil.ROOT, nodeNames);
    }
    
    /**
     * 有参构造方法
     * @param wsUrl
     * @param method
     * @param content
     */
    public SoapRequest(String wsUrl, String method, String content) {
        this.wsUrl = wsUrl;
        this.method = method;
        this.content = content;
    }

    /**
     * webservice地址
     * @return
     */
    public String getWsUrl() {
        return wsUrl;
    }

    /**
     * 设置webservice地址
     * @param wsUrl
     */
    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }


    /**
     * 获取方法名
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置方法名
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取命名空间
     * @return
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * 设置命名空间
     * @param nameSpace
     */
    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    /**
     * 获取内容
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    
    
    
}
