package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/28 21:18
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(13);
        address.setPhone("11122223333");
        address.setName("墨小白");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        Integer count = addressMapper.countByUid(13);
        System.out.println(count);
    }

    @Test
    public void findByUid() {
        List<Address> list = addressMapper.findByUid(13);
        System.out.println(list);
    }

    @Test
    public void findByAid() {
        System.out.println(addressMapper.findByAid(7));
    }

    @Test
    public void updateNoneDefault() {
        addressMapper.updateNoneDefault(13);
    }

    @Test
    public void updateDefaultByAid() {
        addressMapper.updateDefaultByAid(7, "刘强东", new Date());
    }

    @Test
    public void deleteByAid() {
        addressMapper.deleteByAid(3);
    }

    @Test
    public void findLastModified() {
        System.out.println(addressMapper.findLastModified(13));
    }
}
