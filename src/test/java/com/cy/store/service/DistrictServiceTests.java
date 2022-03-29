package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/29 0:32
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {

    @Autowired
    private IDistrictService districtService;

    @Test
    public void getByParent() {
        // 86表示中国，所有省的父代号都是86
        List<District> list = districtService.getByParent("86");
        for (District d : list) {
            System.out.println(d);
        }
    }
}
