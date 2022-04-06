package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
//            address.setAid(null);
//            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        // 检测当前获取到的收货地址数据的归属
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        // 先将所有的收货地址设置为非默认
        Integer rows = addressMapper.updateNoneDefault(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
        // 将用户选中的某条地址设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除数据产生未知的异常");
        }
        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            // 直接终止程序
            return;
        }
        if (result.getIsDefault() == 0) {
            return;
        }
        // 将这条数据中的is_default字段设置为1
        Address address = addressMapper.findLastModified(uid);
        rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        // 根据收货地址数据id，查询收货地址详情
        Address address = addressMapper.findByAid(aid);

        if (address == null) {
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }
}
