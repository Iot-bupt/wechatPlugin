package com.bupt.wechatplugin.service.impl;

import com.bupt.wechatplugin.domain.Device;
import com.bupt.wechatplugin.service.CoreService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 核心服务类 （处理接收消息和推送消息）
 */
@Service("coreService")
public class CoreServiceImpl implements CoreService {

    private static Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);

    /**
     * 处理平台发来的请求
     * @param  deviceMsg
     */
    public Device processRequest(String deviceMsg) {
        logger.info("======= 解析 json 数据 =========");
        logger.info(" ");

        Device device = new Device();
        //Device device1 = JSON.parseObject(device, Device.class);
        JsonObject jsonObject = (JsonObject)new JsonParser().parse(deviceMsg);
        // 获取字段信息
        device.setUserId(jsonObject.get("id").getAsString());  //用户id
        // device.setOpenid(jsonObject.get("openid").getAsString()); //查询数据库获取openid
        device.setOpenid("o0pwj1k3qL9Y_DhBM5GbCGiuqZA8");  // 李尤师兄微信号openid
        device.setDevice(jsonObject.get("device").getAsString()); //设备名称
        device.setNumber(jsonObject.get("number").getAsString());  //设备编号
        device.setWarningMsg(jsonObject.get("warningMsg").getAsString());  //提示信息
        return device;
    }
}
