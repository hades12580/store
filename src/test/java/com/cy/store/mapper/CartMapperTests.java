package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/4/4 15:29
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(13);
        cart.setPid(10000013);
        cart.setNum(2);
        cart.setPrice(15L);
        Integer rows = cartMapper.insert(cart);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateNumByCid() {
        Integer rows = cartMapper.updateNumByCid(1, 5, "购物车管理员", new Date());
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Cart result = cartMapper.findByUidAndPid(13, 10000013);
        System.out.println(result);
    }

    @Test
    public void findVOByUid() {
        List<CartVO> list = cartMapper.findVOByUid(13);
        System.out.println(list);
    }
}
