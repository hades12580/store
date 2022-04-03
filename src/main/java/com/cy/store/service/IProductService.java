package com.cy.store.service;

import com.cy.store.entity.Product;

import java.util.List;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/4/3 19:55
 */
public interface IProductService {
    List<Product> findHotList();

    Product findById(Integer id);
}
