package com.szboanda.shortmessagesend.service.impl;
/******************************************************************************
 p* Copyright (C) 2019 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/



import com.szboanda.shortmessagesend.common.synergymessage.been.SoapRequest;
import com.szboanda.shortmessagesend.common.synergymessage.utils.XmlMapUtil;
import com.szboanda.shortmessagesend.common.utils.JsonHelper;
import com.szboanda.shortmessagesend.utils.HttpUrlConSoapSender;
import com.szboanda.shortmessagesend.utils.SoapSenderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author 尹作迎
 * @title 发送短信的服务
 * @date 2019/11/5
 */
@Service
public class MessageSendService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSendService.class);
    /**
     * 发送短信信息
     * @param para
     * @return
     */
    public Map<String,Object> sendMessage(Map<String, Object> para){

        /**
         * "[
         *    {"search":null,
         *       "TTR":"2019-11-06 11:13:41",
         *      "warnInfo":"数据来源：丽正中间库(kittle)\n数据库最新时间：2019-10-14 10:49:40.0\n数据延迟时间：23天\n",
         *     "sendMobiles":"18372618540,18372618540,18372618540,18372618540,18372618540,18372618540,18372618540,18372618540,18372618540,18372618540,18372618540,18372618540,18372618540,18372618540",
         *     "theme":null,
         *      "objectId":null
         *    }
         *    ]"
         */
        String faillMoiles = "";
        try {
            String businessData = (String) para.get("businessData"); //得到数据
            List<Map<String, Object>> reqBusinessData = JsonHelper.jsonArrayString2List(businessData);
            StringBuilder strFailMobile = new StringBuilder(100);
            for (Map<String, Object> maps : reqBusinessData) {
                //取到电话
                String[] mobiles = ((String) maps.get("sendMobiles")).replaceAll(" ", "").trim().split(",");

                //预警时间
                String TTR = (String) maps.get("TTR");

                //警告信息
                String warnInfo = (String) maps.get("warnInfo");

                String theme = (String) maps.get("theme"); //得到主题

                String messgae = " \n**" + theme + "**\n" + "预警时间:" + TTR + "\n" + warnInfo;  //短信发送信息

                String[] repeatRemoveMobiles = remove(mobiles);
                SoapSenderUtil util = new SoapSenderUtil();

                try {

                    for (int i = 0; i < repeatRemoveMobiles.length; i++) {
                        String mobile = repeatRemoveMobiles[i];
                        if (checkMobile(mobile)) { //手机号码才发送
                            Boolean isS = util.sendMsg(mobile, messgae);    //发送内容和电话
                            if (isS) {
                                strFailMobile.append(mobile + ", ");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return reponseFaillInfo("短信接口调用异常");
                }
            }
            faillMoiles = strFailMobile.toString().replaceAll(", $", "");


        }catch (Exception e){
            LOG.error("解析参数异常：",e);
            return reponseFaillInfo("解析参数异常");
        }
        return reponseSuccessInfo(faillMoiles);
    }


    /**
     * 返回失败信息
     * @return
     */
    private Map<String,Object> reponseFaillInfo(String errmsg){
        Map<String,Object> map = new HashMap<>();
        map.put("errcode",1);  //不为0即可
        map.put("errmsg",errmsg);
        return map;

    }

    /**
     * 返回成功信息
     * @return
     */
    private Map<String,Object> reponseSuccessInfo(String mess){
        Map<String,Object> map = new HashMap<>();
        map.put("errcode",0);
        map.put("errmsg",mess);
        return map;

    }



    //数组去重复
    public  String[] remove(String [] arrStr) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<arrStr.length; i++) {
            if(!list.contains(arrStr[i])) {
                list.add(arrStr[i]);
            }
        }
        //返回一个包含所有对象的指定类型的数组
        String[] newArrStr =  list.toArray(new String[1]);

        return newArrStr;
    }

    /**
     * 检查手机号
     * @param mobileNumber
     * @return
     */
    private boolean checkMobile(String mobileNumber){

        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
