package com.cy.store.service;

import com.cy.store.vo.CartVO;

import java.util.List;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/4/4 15:42
 */

public interface ICartService {
    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户的id
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    List<CartVO> getVOByUid(Integer uid);
}
