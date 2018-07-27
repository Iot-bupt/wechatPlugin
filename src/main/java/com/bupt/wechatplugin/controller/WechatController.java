package com.bupt.wechatplugin.controller;

import com.bupt.wechatplugin.domain.Device;
import com.bupt.wechatplugin.service.core.CoreServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.bupt.wechatplugin.util.weixinUtil;
import com.bupt.wechatplugin.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wechat")
public class WechatController {

    private CoreServiceImpl wechatService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public WechatController(CoreServiceImpl coreService) {
        this.wechatService = coreService;
    }

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

//    @ResponseBody
//    @GetMapping("/test")
//    public String test(){
//        return "Test Succeed";
//    }

    /**
     * 接收平台消息
     * @param request
     */
    @GetMapping("/send")
    public void wechatController(HttpServletRequest request){
//        CoreServiceImpl coreService = new CoreServiceImpl();
        Device device = new Device();

        // 解析平台传过来的json数据,建立用户对象
        logger.info("======= send templateNews =======");
        wechatService.processRequest(request,device);

        // 发送模板消息
        MessageUtil.pushTemplateNews(device.getOpenid(), device.getDevice(), device.getNumber(), device.getWarningMsg());
    }
}

//    @PostMapping(produces = "application/xml; charset=UTF-8")
//    public String post(@RequestBody String requestBody,
//                       @RequestParam("signature") String signature,
//                       @RequestParam("timestamp") String timestamp,
//                       @RequestParam("nonce") String nonce,
//                       @RequestParam(name = "encrypt_type", required = false) String encType,
//                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
//        this.logger.info("\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
//                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
//                        signature, encType, msgSignature, timestamp, nonce, requestBody);
//
//        if (!this.wechatService.checkSignature(timestamp, nonce, signature)) {
//            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
//        }
//
//        String out = null;
//        if (encType == null) {
//            // 明文传输的消息
//            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
//            WxMpXmlOutMessage outMessage = this.route(inMessage);
//            if (outMessage == null) {
//                return "";
//            }
//
//            out = outMessage.toXml();
//        } else if ("aes".equals(encType)) {
//            // aes加密的消息
//            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
//                    requestBody, this.wechatService.getWxMpConfigStorage(), timestamp,
//                    nonce, msgSignature);
//            this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
//            WxMpXmlOutMessage outMessage = this.route(inMessage);
//            if (outMessage == null) {
//                return "";
//            }
//
//            out = outMessage
//                    .toEncryptedXml(this.wechatService.getWxMpConfigStorage());
//        }
//
//        this.logger.debug("\n组装回复信息：{}", out);
//
//        return out;
//    }

//    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
//        try {
//            return this.router.route(message);
//        } catch (Exception e) {
//            this.logger.error(e.getMessage(), e);
//        }
//
//        return null;
//    }




