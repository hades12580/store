package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.service.IOrderService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/4/6 19:30
 */

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        // 调用业务对象执行业务
        Order data = orderService.create(aid, cids, getUidFromSession(session), getUsernameFromSession(session));
        // 返回成功与数据
        return new JsonResult<Order>(OK, data);
    }
}
