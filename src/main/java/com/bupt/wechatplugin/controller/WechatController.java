package com.bupt.wechatplugin.controller;

import com.alibaba.fastjson.JSONObject;
import com.bupt.wechatplugin.domain.TemplateNews;
import com.bupt.wechatplugin.domain.WechatConf;
import com.bupt.wechatplugin.pojo.Oauth2Token;
import com.bupt.wechatplugin.pojo.SNSUserInfo;
import com.bupt.wechatplugin.service.UserService;
import com.bupt.wechatplugin.service.impl.UserServiceImpl;
import com.bupt.wechatplugin.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bupt.wechatplugin.util.weixinUtil;
import com.bupt.wechatplugin.util.MessageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/wechatplugin")
public class WechatController {
    @Autowired
    private WechatConf conf;

    private UserService userService = new UserServiceImpl();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 微信开发者服务器验证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return  若成功返回echostr
     */
    @ResponseBody
    @GetMapping("/verify")
    public String authGet(
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {

        logger.info("======= verify start =======");
        logger.info("signature: "+ signature + "," +
                    "timestamp: " + timestamp + "," +
                    "nonce: " + nonce + "," +
                    "echostr: " + echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (weixinUtil.checkSignature(signature, timestamp, nonce)) {
            logger.info("接入成功");
            return echostr;
        }
        logger.info("接入失败");
        return "error";
    }


//    GET、POST方式提时， 根据request header Content-Type的值来判断:
//
//    1. application/x-www-form-urlencoded， 可选（即非必须，因为这种情况的数据@RequestParam, @ModelAttribute也可以处理，当然@RequestBody也能处理）；
//    2. multipart/form-data, 不能处理（即使用@RequestBody不能处理这种格式的数据）；
//    3. 其他格式， 必须（其他格式包括application/json, application/xml等。这些格式的数据，必须使用@RequestBody来处理）；


    /**
     * 发送模板消息
     * @param alarmMSg 报警信息
     */
    @RequestMapping(value="/sendTemplateMsg", method = RequestMethod.POST )
    public void sendTemplateMsg(@RequestBody String alarmMSg){
        logger.info("============== send templateNews ==============");
        System.out.println("alarmMSg: " + alarmMSg);
        JSONObject msgJson = (JSONObject) JSONObject.parse(alarmMSg);
        String deviceName = msgJson.getString("deviceName");
        String deviceType = msgJson.getString("deviceTypeb");
        String alarmDetail = msgJson.getString("alarmDetail");
        Integer customerId = msgJson.getInteger("customerId");
        String gatewayId = msgJson.getString("gatewayId");
        String template_id = conf.getTemplateid();
        String appid = conf.getAppid();

        // 根据customerid去account模块查找需要发送的用户的 mini_openid列表
        List<String> mini_openids = userService.getAllMiniOpenids(customerId, gatewayId);
        if(mini_openids == null)  return;

        // 查数据库获得Touser列表
        List<String> toUsers = userService.getAllTousers(mini_openids);
        if(toUsers == null)  return;

        // 发送模板消息
        for (String toUser: toUsers) {
            JSONObject data = new JSONObject();
            Date time = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            data.put("first", JsonUtil.setItem("设备报警", conf.getText_color()));  // set first
            data.put("keyword1", JsonUtil.setItem(deviceName, null));  // 设备名
            data.put("keyword2", JsonUtil.setItem(ft.format(time), null));  // 报警时间
            data.put("keyword3", JsonUtil.setItem(alarmDetail, null));  // 报警内容
            data.put("remark", "请您及时处理！");
            TemplateNews tn = new TemplateNews(toUser, template_id, appid, data);
            MessageUtil.pushTemplateNews(tn);
        }
        logger.info("============== success ==============");
    }

    /**
     * 用户关注公众号，存储 unionid 和 oa_openid
     * @param message 获取登录信息
     */
    @RequestMapping(value="/oauth", method = RequestMethod.GET)
    public void oauth(@RequestBody String message){
        logger.info("oauth.......");
        final long serialVersionUID = -1847238807216447030L;
        // 用户同意授权后，能获取到code
        JSONObject msg = (JSONObject) JSONObject.parse(message);
        String code = msg.getString("code");
        String state = msg.getString("state");
        System.out.println("code = " + code);
        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            Oauth2Token weixinOauth2Token = weixinUtil.getOauth2AccessToken(conf.getAppid(), conf.getAppSecret(), code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息（unionid）
            SNSUserInfo snsUserInfo = weixinUtil.getSNSUserInfo(accessToken, openId);
            String unionid = snsUserInfo.getUnionid();
            // 查找数据库是否存在，若不存在则插入
            userService.judge_exist_and_follow(unionid,null,openId);
        }
    }

    /**
     * 小程序检查是否关注公众号
     * @param message
     * @return
     */
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String judgeFollow(@RequestBody JSONObject message){
        String mini_openid = message.getString("mini_openid");
        String unionid = message.getString("unionid");
        String result = userService.judge_exist_and_follow(unionid,mini_openid,null);
        return result;
    }
}
