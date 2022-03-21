package com.cy.store.service;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/21 19:24
 */

import com.cy.store.entity.User;

/**用户模块业务接口*/
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);
}
