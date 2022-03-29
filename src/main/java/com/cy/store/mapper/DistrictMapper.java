package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/29 20:12
 */

@Mapper
public interface DistrictMapper {
    /**
     * 根据用户的父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所有区域列表
     */
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
