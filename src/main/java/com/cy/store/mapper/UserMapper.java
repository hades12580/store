package com.cy.store.mapper;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/20 23:23
 */

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/** 处理用户数据操作的持久层接口 */
@Mapper
public interface UserMapper {
    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUsername(String username);

    /**
     * 根据uid更新用户的密码
     * @param uid 用户的id
     * @param password 新密码
     * @param modifiedUser 最后修改执行人
     * @param modifiedTime 最后修改时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id查询用户数据
     * @param uid 用户id
     * @return 匹配的用户数据，如果没有匹配的用户数据，则返回null
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据信息
     * @param user 用户数据
     * @return 返回值为受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @Param("SQL映射文件中#{}占位符的变量名"):-当SQL语句的占位符和映射的几口方法参数名不一致时，
     * 需要将某个参数强行注入到某个占位符变量上时，可以使用@Param()来标注映射的关系
     * 根据用户uid修改用户头像
     * @param uid 用户uid
     * @param avatar 头像
     * @param modifiedUser 最后修改执行人
     * @param modifiedTime 最后修改时间
     * @return 受影响的行数
     */
    Integer updateAvatarByUid(
            @Param("uid") Integer uid,
            @Param("avatar") String avatar,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime
    );
}
