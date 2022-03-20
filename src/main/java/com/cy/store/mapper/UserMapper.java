package com.cy.store.mapper;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/20 23:23
 */

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/** 用户模块的持久层接口*/
// @Mapper
@Repository
public interface UserMapper {
    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 受影响的行数（增删改）
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 找到对应用户则返回该用户数据，未找到则返回null值
     */
    User findByUsername(String username);
}
