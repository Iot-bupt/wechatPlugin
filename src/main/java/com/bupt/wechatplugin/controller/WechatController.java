package com.bupt.wechatplugin.controller;

import com.bupt.wechatplugin.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/wechatplugin")
public class WechatController {

    @Autowired
    private WechatService wechatService;


}
