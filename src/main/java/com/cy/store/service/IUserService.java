package com.cy.store.service;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/21 19:24
 */

import com.cy.store.entity.User;

/** 处理用户数据的业务层接口 */
public interface IUserService {
    /**
     * 用户注册
     * @param user 用户数据
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户数据
     */
    User login(String username, String password);

    /**
     * 修改密码
     * @param uid 当前登录的用户id
     * @param username 用户名
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword);
}
