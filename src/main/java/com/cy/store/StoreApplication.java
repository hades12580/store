package com.cy.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// MapperScan注解指定当前项目中的mapper接口路径位置，在项目启动时自动加载所有接口文件
@MapperScan("com.cy.store.mapper")
public class StoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(StoreApplication.class, args);
    }

}
