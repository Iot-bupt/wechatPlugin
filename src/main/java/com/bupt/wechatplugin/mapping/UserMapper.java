package com.bupt.wechatplugin.mapping;

import com.bupt.wechatplugin.pojo.MP2OA;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description:利用Mybatis的注解实现数据的增删查改
 */
// 加上 Mapper 注解，在项目启动时可以扫描到
@Mapper
public interface UserMapper {
    @Select("select mini_openid from mp2oa where unionid = #{unionid}")
    String selectMiniOpenidByUnionid(@Param("unionid")String unionid);

    @Select("select oa_openid from mp2oa where mini_openid = #{mini_openid}")
    String selectOaOpenidByMiniOpenid(@Param("mini_openid")String mini_openid);

    @Select("select * from mp2oa where unionid = #{unionid}")
    MP2OA selectByUnionid(@Param("unionid")String unionid);

    @Insert("insert into mp2oa(unionid,mini_openid,oa_openid) values(#{unionid},#{mini_openid},#{oa_openid})")
    void insert(@Param("unionid")String unionid, @Param("mini_openid")String mini_openid, @Param("oa_openid")String oa_openid);

    @Update("update mp2oa set mini_openid = #{mini_openid} where unionid = #{unionid}")
    void updateMiniOpenid(@Param("unionid")String unionid, @Param("mini_openid")String mini_openid);

    @Update("update mp2oa set oa_openid = #{oa_openid} where unionid = #{unionid}")
    void updateOaOpenid(@Param("unionid")String unionid, @Param("oa_openid")String oa_openid);

    @Select("select binder from user_relation where binded = #{customerid}")
    List<Integer> selectAllCustomers(@Param("customerid") Integer customerid);

    @Select("select openid from user_new where id = #{customerid}")
    String selectMiniOpenid(@Param("customerid")Integer customerid);

//    @Select("select * from wechat")
//    List<wechat> findAll();
//
//    @Select("select * from wechat where nickname = #{nickname}")  // 用 #{ param } 的形式支持动态 sql
//    wechat findByNickName(@Param("nickname") String nickname);  //  @Param中的参数名称要和 sql 语句中的参数名称一致
//
//    @Insert("insert into wechat(id, nickname, openId) values(#{id},#{nickname}, #{openid})")
//    int insert(@Param("id") String id, @Param("nickname") String nickname, @Param("openid") String openid);
//
//    @Update("update wechat set openId=#{openid} where nickname=#{nickname}")
//    int updateOpenid(@Param("nickname") String nickname, @Param("openid") String openid);
//
//    @Delete("delete from wechat where id=#{id}")
//    int delete(@Param("id") String id);

}
