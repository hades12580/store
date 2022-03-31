package com.cy.store.mapper;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/28 20:01
 */

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    /**
     * 根据用户id查询用户收货地址数据
     * @param uid 用户id
     * @return 收货地址数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     * @param aid 收货地址id
     * @return 收货地址数据，如果没有找到则返回null值
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户uid值来修改用户收货地址设置为非默认
     * @param uid 用户id
     * @return 受影响的行数
     */
    Integer updateNoneDefault(Integer uid);

    /**
     * 根据收货地址id设置默认地址
     * @param aid 收货地址id
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(@Param("aid") Integer aid,
                               @Param("modifiedUser") String modifiedUser,
                               @Param("modifiedTime") Date modifiedTime);
}
