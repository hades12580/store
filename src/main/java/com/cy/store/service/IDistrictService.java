package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/29 20:28
 */

public interface IDistrictService {
    /**
     * 根据父区域代号查询区域的信息(省市区)
     * @param parent 父区域代号
     * @return 多个区域信息
     */
    List<District> getByParent(String parent);

    String getNameByCode(String code);
}
