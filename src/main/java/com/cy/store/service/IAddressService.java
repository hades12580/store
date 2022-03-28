package com.cy.store.service;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/28 22:10
 */

import com.cy.store.entity.Address;

/** 收货地址业务层接口 */
public interface IAddressService {
    void addNewAddress(Integer uid, String username, Address address);
}
