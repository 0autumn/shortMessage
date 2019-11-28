package com.szboanda.shortmessagesend.controller;
/******************************************************************************
 p* Copyright (C) 2019 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

import com.szboanda.shortmessagesend.service.impl.MessageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 尹作迎
 * @title
 * @date 2019/11/5
 */
@Controller
@RequestMapping("/message")
public class MessageSendController {

 @Autowired
 MessageSendService messageSendService;

 @RequestMapping(value="/sendtomessage",method = RequestMethod.POST)
 @ResponseBody
 public Map<String,Object>  sendToShortMessage(@RequestBody Map<String, Object> para){

     return messageSendService.sendMessage(para);
 }


}
