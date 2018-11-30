package com.bupt.wechatplugin.pojo;

/**
 * description auth token
 * @author zhs
 * createTime : 2018-11-28
 */
public class Token {
    private String access_token;
    private int expiresIn;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
