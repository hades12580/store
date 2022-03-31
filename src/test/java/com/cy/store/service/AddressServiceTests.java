package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/29 0:32
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setPhone("99999999999");
        address.setName("尼卡");
        addressService.addNewAddress(13, "管理员", address);
    }

    @Test
    public void setDefault() {
        addressService.setDefault(4, 13, "艾尔登之王");
    }
}
