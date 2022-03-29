package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/28 22:10
 */

/** 新增收货地址的实现类 */
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;
    // 在添加用户收货地址的业务层依赖于IDistrictService的业务层接口
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        // 调用收货地址统计的方法
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用户收货地址数量超出上限");
        }

        // 对address对象中的数据进行补全:省市区
        String provinceName= districtService.getNameByCode(address.getProvinceCode());
        String cityName= districtService.getNameByCode(address.getCityCode());
        String areaName= districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        // uid、isDefault
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0; // 1表示默认，0表示不默认
        address.setIsDefault(isDefault);
        // 补全4项日志
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        // 调用插入收货地址的方法
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入用户收货地址产生未知异常");
        }
    }
}
