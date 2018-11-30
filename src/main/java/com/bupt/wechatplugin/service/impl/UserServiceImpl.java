package com.bupt.wechatplugin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bupt.wechatplugin.mapping.UserMapper;
import com.bupt.wechatplugin.pojo.MP2OA;
import com.bupt.wechatplugin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> getAllTousers(List<String> mini_openids) {
        List<String> toUsers = new ArrayList<>();
        for (String mini_openid : mini_openids){
            toUsers.add(getTouser(mini_openid));
        }
        return toUsers;
    }

    @Override
    public String getTouser(String mini_openid) {
        return userMapper.selectOaOpenidByMiniOpenid(mini_openid);
    }

    @Override
    public String judge_exist_and_follow(String unionid, String mini_openid, String oa_oepnid) {
        MP2OA user = userMapper.selectByUnionid(unionid);
        JSONObject result = new JSONObject();
        if(user != null){ // 该用户存在
            if(user.getOa_openid() == null){  // 用户未注册公众号openid
                result.put("code", "unfollow");
                if(oa_oepnid != null){
                    userMapper.updateOaOpenid(unionid,oa_oepnid);
                }
            } else {
                result.put("code", "followed");
                result.put("oa_openid",user.getOa_openid());
            }
            if (user.getMini_openid() == null){ // 用户未注册小程序openid
                if(mini_openid != null) {
                    userMapper.updateMiniOpenid(unionid, mini_openid);
                }
            }
        }else{  // 用户不存在，添加进数据库
            userMapper.insert(unionid, mini_openid, oa_oepnid);
            result.put("code", "unfollow");
        }
        return result.toJSONString();
    }

    @Override
    public List<String> getAllMiniOpenids(Integer customerid, String gatewayid) {
        List<String> mini_openids = new ArrayList<>();
        List<Integer> customerids = userMapper.selectAllCustomers(customerid);
        for (Integer id : customerids){
            String mini_openid = userMapper.selectMiniOpenid(id);
            if(mini_openid != null){
                mini_openids.add(mini_openid);
            }
        }
        return mini_openids;
    }


//    @Override
//    public List<wechat> findAll() {
//        return userMapper.findAll();
//    }
//
//    @Override
//    public wechat findByNickName(String nickname) {
//        return userMapper.findByNickName(nickname);
//    }
//
//    @Override
//    public int insert(String id, String nickname, String openid) {
//        return userMapper.insert(id, nickname, openid);
//    }
//
//    @Override
//    public int updateOpenid(String nickname,String openid) {
//        return userMapper.updateOpenid(nickname,openid);
//    }
//
//    @Override
//    public int delete(String id) {
//        return userMapper.delete(id);
//    }
}
