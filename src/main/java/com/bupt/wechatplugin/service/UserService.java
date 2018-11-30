package com.bupt.wechatplugin.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface UserService {
    // 获取tousers
    List<String> getAllTousers(List<String> mini_openids);
    String getTouser(String mini_openid);
    // 查数据库判断用户是否存在 及 是否关注微信公众号
    String judge_exist_and_follow(String unionid, String mini_openid, String oa_openid);
    List<String> getAllMiniOpenids(Integer customerid,String gatewayid);

//    /**
//     * 查找所有用户
//     * @return
//     */
//    List<wechat> findAll();
//
//    /**
//     * 根据微信名查找用户
//     * @param nickname
//     * @return
//     */
//    wechat findByNickName(String nickname);
//
//    /**
//     * 插入用户
//     * @param id
//     * @param nickname
//     * @param openid
//     */
//    int insert(String id, String nickname, String openid);
//
//    /**
//     * 更新 openid
//     * @param user
//     */
//    int updateOpenid(String nickname, String openid);
//
//    /**
//     * 根据 id 删除用户
//     * @param id
//     */
//    int delete(String id);

}
