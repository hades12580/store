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
```
insert into t_user (username, password) values (list)
```
* 用户注册时首先要查询当前用户名是否存在，如果存在则不能进行注册。相当于查询语句
```
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
#### 4.1 规划异常
* RuntimeException异常，作为这个异常的子类，然后再去定义具体的异常类型来继承这个异常。业务层异常的基类-ServiceException异常，这个异常继承RuntimeException。异常机制的建立。
* 根据业务层不同功能来详细定义具体的异常类型，统一继承ServiceException异常类。
* 用户在进行注册时可能会产生用户名被占用的错误，抛出一个异常：UsernameDuplicatedException
* 正在执行数据插入操作时，服务器、数据库宕机。处于正在执行插入过程中所产生的异常：InsertException
#### 4.2 设计接口和抽象方法
* 在service包下创建一个IUserService接口。
* 创建一个实现类UserServiceImpl类，需要实现IUserService接口，并且实现抽象方法
* 在单元测试包下创建一个UserServiceTests类，在这个类中添加单元测试功能
### 5 注册-控制层
#### 5.1 创建响应
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
#### 5.2 设计请求
* 依据当前的业务功能模块进行请求的设计。
```
请求路径：/user/reg
请求参数：User user
请求类型：POST
响应结果：JsonResult<void>
```
#### 5.3 处理请求
* 创建一个控制层对应的类UserController类。依赖于业务层接口
#### 5.4 控制层优化设计
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
* 当用户输入用户名和密码将数据提交给后台数据库进行查询，如果存在对应的用户名和密码则表示登陆成功，登陆成功后跳转到系统主页-index.html页面，跳转在前段使用jquery来完成。
### 1 登陆-持久层
#### 1.1 规划需要执行的SQL语句
* 依据用户提交的用户名和密码做select查询。密码的比较在业务层执行。
```
select * from t_user where username=?
```
* 说明：如果在分析过程中发现某个功能模块已经被开发完成，就可以省略当前开发步骤，分析过程不能省略。
#### 1.2 接口设计和方法
* 不用重复开发。单元测试也无需单独执行。
### 2 登陆-业务层
#### 2.1 规划异常
* 用户名对应的密码错误，密码匹配失败的异常：PasswordNotMatchException异常，运行时异常，业务异常。
* 用户名未被找到，抛出异常：UserNotFoundException异常，运行时异常，业务异常。
* 异常的编写：
  * 业务层异常需要继承ServiceException异常类。
  * 在具体的异常类中定义构造方法(可以使用快捷键生成，有5个构造方法)。
#### 2.2 设计业务层接口和抽象方法
* 直接在IUserService接口中编写抽象方法，login(String username, String password)。将当前登陆成功的用户数据以当前用户对象的形式进行返回。状态管理：可以将数据保存在cookie或session中，可以避免重复度很高的数据多次频繁操作数据进行获取(用户名、用户id-存放在session中，用户头像-保存在cookie中)。
* 需要在实现类中实现父接口中的抽象方法。
* 在测试类中测试业务层登陆的方法是否可以执行通过。
* ps:如果一个类没有手动创建而是直接复制的项目中，IDEA会找不到这个类。原因在于之前的缓存导致不能够正常找到这个类的符号。重新构建一下可以解决。
#### 2.3 抽象方法实现
### 3 登陆-控制层
#### 3.1 处理异常
* 业务层抛出的异常是什么，需要在统一异常处理类中进行统一的捕获和处理，如果业务层抛出的异常类型已经在统一异常处理类中处理过，则不需要重复添加
#### 3.2 设计请求
```
请求路径：/users/login
请求方式：POST
请求数据：String username, String password, HttpSession session
响应结果：JsonResult<User>
```
#### 3.3 处理请求
* 在UserController类中编写处理请求的方法。
### 4 登陆-前端页面
* 在login.html页面中依据前面所设置的请求来发送ajax请求。
* 访问页面进行用户的登陆操作

##用户会话session
* session对象主要存在服务器端，可以用于保存服务器的临时数据对象，所保存的数据可以在整个项目中都可以通过访问来获取，把session的数据看做一个共享的数据。首次登陆的时候所获取的用户数据转移到session对象即可。session.getAttribute("key")可以将获取session中的数据这种行为进行封装，封装在BaseController类中。
* 封装session对象中数据的获取(封装父类中)、数据的设置(当用户登陆成功时进行数据的设置，设置到全局的session对象)。
* 在父类(BaseController)中封装两个数据：获取uid和获取username对应的两个方法。用户头像暂时不考虑，将来封装到cookie中。
* 在登陆方法中将数据封装在session对象中。服务器本身自动创建有session对象，已经是一个全局的session对象。SpringBoot直接使用session对象，直接将HttpSession类型的对象作为请求处理方法的参数，会自动将全局的session对象注入到请求处理方法的session形参上。

## 拦截器
* 拦截器：首先将所有的请求统一拦截到拦截器中，可以在拦截器中定义过滤规则，如果不满足系统设置的过滤规则，统一处理是重新打开login.html页面(重定向或转发)，推荐使用重定向。
* 在SpringBoot项目中拦截器的定义和使用：SpringBoot是依靠SpringMVC来完成，SpringMVC提供了一个HandlerInterceptor接口，用于表示定义一个拦截器。
  * 首先自定义一个类，用这个类实现HandlerInterceptor接口。
  * 注册过滤器：添加白名单(可以在不登录情况下访问的资源：register\login\reg\index\product.html)、添加黑名单(在用户登录的状态下才能访问的资源)。
  * 注册过滤技术：借助WebMvcConfigure接口，可以将用户定义的拦截器进行注册，才可以保证拦截器能够生效和使用。定义一个类，然后让这个类实现WebMvcConfigure接口。配置信息，建议存放在项目的config包结构下。
  ```
  // 将自定义的拦截器进行注册
  default void addInterceptors(InterceptorRegistry registry) {
    }
  ``` 
  * 提示重定向次数过多，login.html页面无法打开。解决方案：将浏览器cookie清除，再将浏览器设置为初始设置。
  * 源码解析：
```java
  public interface HandlerInterceptor {
    // 在调用所有处理请求方法之前被自动调用执行的方法
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    // 在ModelAndView对象返回之后被调用的方法
    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    // 在整个请求所有关联的资源被执行完毕最后执行的方法
    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
```

## 修改密码
* 需要用户提交原始密码和新密码，再根据当前登录的用户进行信息的修改操作
### 1 修改密码-持久层
#### 1.1 规划需要执行的SQL语句
* 根据用户的uid修改用户的password值。
```
update t_user set password=?,modified_user=?,modified_time=? where uid=?
```
* 根据uid查询用户数据。在修改密码之前，要保证用户的数据存在、检测是否被标记未删除、检测输入的原始密码是否正确。
```
select * from t_user where uid=?
```
#### 1.2 设计接口和抽象方法
* UserMapper接口，将以上的两个方法的抽象定义出来。将来映射到sql语句上。
#### 1.3 SQL的映射
* 配置到映射文件UserMapper.xml中。
* 做单元测试功能测试。
### 2 修改密码-业务层
#### 2.1 规划异常
* 用户的原密码错误、i_delete==1、uid找不到-->在用户没有发现的异常
* update在更新时，有可能产生未知的异常-->UpdateException
#### 2.2 设计接口和抽象方法
* 执行用户修改密码的核心方法。
```
void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);
```
* 在实现类中实现当前的抽象方法。
* 在单元测试类中编写测试方法 
### 3 修改密码-控制层
#### 3.1 处理异常
* UpdateException需要配置在统一的异常处理方法中。
#### 3.2 设计请求
```
/users/change_password
post
String oldPassword, String newPassWord, HttpSession session //需要和表单中的name属性值保持一致
JsonResult<Void>
```
#### 3.3 处理请求
### 4 修改密码-前端控制
* password.html中添加ajax请求处理，不再手动编写ajax结构，直接复制，然后再微调修改参数即可

## 个人资料
### 1 个人资料-持久层
#### 1.1 需要规划SQL语句
* 更新用户信息的SQL语句
```
update t_user set phone=?, email=?, gender=?, modified_user=?, modified_time=? where uid=?
```
* 根据用户名查询用户的数据(查询用户数据不需要再重复开发)
```
select * from t_user where uid=?
```
#### 1.2 接口与抽象方法
* 更新用户信息方法的定义。
#### 1.3 抽象方法的映射
* 在UserMapper.xml文件中进行方法的映射编写。
* 在测试类中完成功能的测试
### 2 个人资料-业务层
#### 2.1 异常规划
* 设计两个功能：
  * 当打开页面时获取用户信息并填充到对应的文本框中。
  * 检测用户是否点击了修改按钮，如果检测到则执行修改用户信息的操作。
* 打开页面时可能找不到用户数据、点击删除按钮之前需要再次检测用户数据是否存在
#### 2.2 接口和抽象方法
* 主要有两个功能的模块，对应的是两个抽象方法的设计。
```
/**
 * 根据用户的id查询用户数据
 * @param uid 用户id
 * @return 用户的数据
 */
User getByUid(Integer uid);

/**
 * 更新用户数据的操作
 * @param uid 用户id
 * @param username 用户名称
 * @param user 用户对象数据
 */
void changeInfo(Integer uid, String username, User user);
```
#### 2.3 实现抽象方法
* 在UserServiceImpl类中添加两个抽象方法的具体实现。
* 在测试类中进行功能的单元测试。
### 3 个人资料-控制层
#### 3.1 处理异常
* 暂无
#### 3.2 设计请求
* 设置-一打开页面就发送当前用户数据的查询。
```
/users/get_by_uid
GET
HttpSession session
JsonResult<User>
```
* 点击修改按钮发送用户数据的修改操作请求的设计。
```
/users/change_info
POST
User user, HttpSession session
JsonResult<Void>
```
#### 3.3 处理请求
* 将上述两个设计在控制层进行编写
### 4 个人资料-前端页面
* 在打开userdata.html页面自动发送ajax请求，查询到的数据填充到页面上。
* 在检测到用户点击修改按钮后发送一个ajax请求。

## 上传头像
### 1 上传头像-持久层
#### 1.1 规划SQL语句
* 将对象文件保存在操作系统上，然后把这个文件的路径记录下来，因为记录路径非常方便，可以依据路径去找这个文件，在数据库中保存这个文件路径即可。将所有静态资源(图片、文件、其他资源文件)放在某台电脑上，作为单独的服务器使用。-对应的是一个更新用户avatar字段的SQL语句
```
update t_user set avatar=?, modified_user=?, modified_time=? where uid=?
```
#### 1.2 设计接口与抽象方法
* UserMapper接口中定义抽象方法用于修改用户头像
#### 1.3 抽象方法的映射
* UserMapper.xml文件中编写映射的SQL语句
* 在测试类中编写测试方法
### 2 上传头像-业务层
#### 2.1 规划异常
* ~~用户数据不存在，找不到对应用户的数据~~
* ~~更新数据时，各种未知异常的产生~~
* _无需重复开发_
#### 2.2 设计接口和抽象方法
#### 2.3 实现抽象方法
* 编写业务层更新用户头像的方法
* 测试业务层方法执行
### 3 上传头像-控制层
#### 3.1 规划异常
```
文件异常的父类：
  FileUploadException泛指文件上传的异常-作为父类继承RuntimeException
  
父类是FileUploadException：
  FileEmptyException 文件为空的异常
  FileSizeException 文件大小超出异常
  FileTypeException 文件类型异常
  FileUploadIOException 文件读写的异常
  FileStateException 文件状态的异常
```
* 五个构造方法显式的声明出来，再去继承相关的父类。
#### 3.2 处理异常
* 在基类BaseController类中进行编写和统一处理。
```
else if (e instanceof FileEmptyException) {
    result.setState(6000);
} else if (e instanceof FileSizeException) {
    result.setState(6001);
} else if (e instanceof FileTypeException) {
    result.setState(6002);
} else if (e instanceof FileStateException) {
    result.setState(6003);
} else if (e instanceof FileUploadIOException) {
    result.setState(6004);
}
```
* 在异常统一处理方法的参数列表上增加新的异常处理作为它的参数
```
@ExceptionHandler({ServiceException.class, FileUploadException.class})
```
#### 3.3 设计请求
```
/users/change_avatar
POST (GET请求提交数据2KB，太小，提交不了图片)
HttpSession session, MultipartFile file
JsonResult<String> 
```
#### 3.4 实现请求
### 4 上传头像-前端页面
* 在upload页面中编写上传头像的代码
  * 说明：如果直接使用表单进行上传，需要给表单显式的添加一个属性enctype="multipart/form-data"声明出来，不会将目标文件的数据格式做修改再上传，不同于字符串。