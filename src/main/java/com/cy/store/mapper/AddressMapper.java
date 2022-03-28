package com.cy.store.mapper;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/28 20:01
 */

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Mapper;

/** 收货地址持久层的接口 */
@Mapper
public interface AddressMapper {

    /**
     * 插入用户的收货地址数据
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户id统计收货地址数量
     * @param uid 用户的id
     * @return 当前用户收货地址总数
     */
    Integer countByUid(Integer uid);
}
