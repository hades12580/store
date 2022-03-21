# 工程简介
## 练手用的电脑商城项目

# 项目纪录日志
## 用户注册
### 1 创建数据表
### 2 创建用户实体类
### 3 注册-持久层
_通过MyBatis操作数据库，做MyBatis开发流程_
#### 3.1 规划需要执行的SQL语句
* 用户注册功能，相当于在做数据的插入操作：
```sql
insert into t_user (username, password) values (list)
```
* 用户注册时首先要查询当前用户名是否存在，如果存在则不能进行注册。相当于查询语句
```sql
select * from t_user where username=？
```
#### 3.2 设计接口和抽象方法
* 定义Mapper接口。在项目目录结构下首先创建一个mapper包，在这个包下再根据不同的功能模块创建mapper接口。创建一个UserMapper接口，在接口中定义两个SQL语句抽象方法。
* 在启动类配置mapper接口文件的位置
```
@MapperScan("com.cy.mapper")
```
#### 3.3 编写映射
* 定义xml映射文件，与对应接口进行关联。所有映射文件需要放在resource目录下，在该目录下创建一个mapper文件夹，然后在这个文件夹下存放Mapper映射文件。
* 创建接口对应的映射文件，遵循和接口名称保持一致的原则。创建一个UserMapper.xml文件。
* 配置接口中的方法对应SQL语句，需借助标签完成，insert/update/delete/select
* 将mapper文件的位置注册到properties对应的配置文件中
* 单元测试：每个独立的层编写完毕后需要编写单元测试方法，测试当前的功能。在test包结构下创建一个mapper包，在这个包下再创建持久层的测试。
### 4 注册-业务层
#### 4.1规划异常
* RuntimeException异常，作为这个异常的子类，然后再去定义具体的异常类型来继承这个异常。业务层异常的基类-ServiceException异常，这个异常继承RuntimeException。异常机制的建立。
* 根据业务层不同功能来详细定义具体的异常类型，统一继承ServiceException异常类。
* 用户在进行注册时可能会产生用户名被占用的错误，抛出一个异常：UsernameDuplicatedException
* 正在执行数据插入操作时，服务器、数据库宕机。处于正在执行插入过程中所产生的异常：InsertException
#### 4.2设计接口和抽象方法
* 在service包下创建一个IUserService接口。
* 创建一个实现类UserServiceImpl类，需要实现IUserService接口，并且实现抽象方法
* 在单元测试包下创建一个UserServiceTests类，在这个类中添加单元测试功能
### 5 注册-控制层
#### 5.1创建响应
##### 状态码、状态描述信息、数据。将这部分功能封装到一个类中，将该类作为方法的返回值，返回给前段浏览器
```java
public class JsonResult<E> implements Serializable {
    /**状态码*/
    private Integer state;
    /**描述信息*/
    private String message;
    /**数据*/
    private E data;
}
```
#### 5.2设计请求
* 依据当前的业务功能模块进行请求的设计。
```
请求路径：/user/reg
请求参数：User user
请求类型：POST
响应结果：JsonResult<void>
```
#### 5.3处理请求
* 创建一个控制层对应的类UserController类。依赖于业务层接口
#### 5.4控制层优化设计
* 在控制层抽离一个父类，在这个父类中统一处理关于异常的相关操作。编写一个BaseController类，统一处理异常。
* 重新构建了reg()方法
```
@RequestMapping("reg")
// @RequestBody // 表示此方法的响应结果以json格式进行数据的响应给到前段
public JsonResult<Void> reg(User user) {
    userService.reg(user);
    return new JsonResult<>(OK);
}
```
### 6 注册-前端页面
* 在register页面中编写发送请求的方法，通过点击事件来完成。选中对应的按钮($("选择器"))，再去添加点击事件，$.ajax()函数发送异步请求
* JQuery封装了一个函数，称之为$.ajax()函数，通过对象调用ajax()函数，可以异步加载相关的请求。依靠的是JavaScript提供的一个对象XHR(XmlHttpResponse)，封装了这个对象。
* ajax()使用方式。需要传递一个方法体作为方法的参数来使用，大括号代表方法体。ajax接收多个参数，参数间用","分隔，每一组参数间用";"分隔，参数的组成部分一个是参数名称(不能随意定义)，参数的值要求用字符串标识。参数的声明顺序没有要求。语法结构：
```js
$.ajax({
    url: "",
    type: "",
    data: "",
    dataType: "",
    success: function () {
        
    },
    error: function () {
        
    }
});
```
* ajax()函数参数的含义：
```
|参数     |功能描述                                                                                   
|--------|------------------------------------------------------------------------------------------
|url     |标识请求的地址(url地址)，不能包含参数列表部分的内容。例如：url:"localhost:8080/users/reg"           
|type    |请求类型(GET和POST请求的类型)。例如：type:"POST"                                               
|data    |向指定的请求url地址提交的数据。例如：data:"username=tom&pwd=123456                              
|dataType|提交的数据类型，一般指定为json类型。例如：dataType:"json"                                        
|success |当服务器正常响应客户端时，会自动调用success参数的方法，并且将服务器返回的数据以参数形式传递给这个方法的参数上
|error   |当服务器未正常响应客户端时，会自动调用error参数的方法，并且将服务器返回的数据以参数形式传递给这个方法的参数上 
```
* js代码可以独立声明在一个js文件中或声明在一个script标签中。
* js代码无法正常被服务器解析执行，体现在点击页面中的按钮没有任何响应。解决方案：
  * 在项目的maven下clear清理项目-install重新部署
  * 在项目的file下-cash清理缓存
  * 重新构建项目：build-rebuild
  * 重启IDEA
  * 重启电脑

## 用户登录