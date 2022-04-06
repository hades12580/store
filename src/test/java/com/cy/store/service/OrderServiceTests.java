package com.cy.store.service;

import com.cy.store.entity.Order;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/4/6 19:23
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTests {
    @Autowired
    private IOrderService orderService;

    @Test
    public void create() {
        try {
            Integer aid = 19;
            Integer[] cids = {4, 5, 6, 10, 15};
            Integer uid = 13;
            String username = "订单管理员";
            Order order = orderService.create(aid, cids, uid, username);
            System.out.println(order);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
