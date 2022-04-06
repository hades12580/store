package com.cy.store.mapper;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/4/6 18:00
 */

import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/** 处理订单及订单商品数据的持久层接口 */
@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order 订单数据
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单商品数据
     * @param orderItem 订单商品数据
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);
}
