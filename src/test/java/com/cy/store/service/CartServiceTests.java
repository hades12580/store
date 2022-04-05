package com.cy.store.service;

import com.cy.store.service.ex.ServiceException;
import com.cy.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/4/4 16:00
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceTests {
    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        cartService.addToCart(13, 10000013, 3, "马化腾");
    }

    @Test
    public void getVOByUid() {
        List<CartVO> list = cartService.getVOByUid(13);
        System.out.println("count=" + list.size());
        for (CartVO item : list) {
            System.out.println(item);
        }
    }
}
