package com.bupt.wechatplugin.service;

import com.bupt.wechatplugin.domain.Device;

import javax.servlet.http.HttpServletRequest;

public interface CoreService {
    Device processRequest(String deviceMsg) ;
}
