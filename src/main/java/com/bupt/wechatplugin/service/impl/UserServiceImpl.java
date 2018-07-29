package com.bupt.wechatplugin.service.impl;

import com.bupt.wechatplugin.domain.wechat;
import com.bupt.wechatplugin.mapping.UserMapper;
import com.bupt.wechatplugin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<wechat> findAll() {
        return userMapper.findAll();
    }

    @Override
    public wechat findByNickName(String nickname) {
        return userMapper.findByNickName(nickname);
    }

    @Override
    public int insert(String id, String nickname, String openid) {
        return userMapper.insert(id, nickname, openid);
    }

    @Override
    public int updateOpenid(String nickname,String openid) {
        return userMapper.updateOpenid(nickname,openid);
    }

    @Override
    public int delete(String id) {
        return userMapper.delete(id);
    }
}
