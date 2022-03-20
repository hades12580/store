# 工程简介

# 用户注册
    1 创建数据表

    2 创建用户实体类

    3 注册-持久层
        通过MyBatis操作数据库，做MyBatis开发流程

        3.1 规划需要执行的SQL语句
            1-用户注册功能，相当于在做数据的插入操作：
            `insert into t_user (username, password) values (list)`

            2-用户注册时首先要查询当前用户名是否存在，如果存在则不能进行注册。相当于查询语句
            `select * from t_user where username=？`

        3.2 设计接口和抽象方法

            1-定义Mapper接口。在项目目录结构下首先创建一个mapper包，在这个包下再根据不同的功能模
            块创建mapper接口。创建一个UserMapper接口，在接口中定义两个SQL语句抽象方法。

            2-在启动类配置mapper接口文件的位置
            `@MapperScan("com.cy.mapper")`

        3.3 编写映射

            1-定义xml映射文件，与对应接口进行关联。所有映射文件需要放在resource目录下，在该目录下
            创建一个mapper文件夹，然后在这个文件夹下存放Mapper映射文件。

            2-创建接口对应的映射文件，遵循和接口名称保持一致的原则。创建一个UserMapper.xml文件。

            3-配置接口中的方法对应SQL语句，需借助标签完成，insert/update/delete/select

            4-将mapper文件的位置注册到properties对应的配置文件中

            5-单元测试：每个独立的层编写完毕后需要编写单元测试方法，测试当前的功能。在test包结构下创
            建一个mapper包，在这个包下再创建持久层的测试。

    4 注册-业务层

    5 注册-控制层

    6 注册-前端页面