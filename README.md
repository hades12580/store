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
```mysql
insert into t_user (username, password) values (#{username},#{password})
```
* 用户注册时首先要查询当前用户名是否存在，如果存在则不能进行注册。相当于查询语句
```mysql
select * from t_user where username=#{username}
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
```mysql
select * from t_user where username=#{username}
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
```mysql
update t_user set password=#{password},modified_user=#{modifiedUser},modified_time=#{modifiedTime} where uid=#{uid}
```
* 根据uid查询用户数据。在修改密码之前，要保证用户的数据存在、检测是否被标记未删除、检测输入的原始密码是否正确。
```mysql
select * from t_user where uid=#{uid}
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
```mysql
select * from t_user where uid=#{uid}
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
```mysql
update t_user set avatar=#{avatar}, modified_user=#{modifiedUser}, modified_time=#{modifiedTime} where uid=#{uid}
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
### 5 解决Bug
#### 5.1 更改默认大小限制
* SpringMVC默认为1MB文件可以进行上传，手动去修改SpringMVC默认上传文件的大小。
  * 方式1：直接在配置文件中进行配置。
    * ```
      spring.servlet.multipart.max-file-size=10MB
      spring.servlet.multipart.max-request-size=15MB
      ```
  * 方式2：采用Java代码的形式设置文件上传大小的限制。主类中进行配置，定义一个方法，必须使用@Bean修饰符来修饰。在类的前面添加@Configuration注解修饰类。MultipartConfigElement类型。
    * ```
      @Bean
      public MultipartConfigElement getMultipartConfigElement() {
      // 创建一个配置的工厂类对象
      MultipartConfigFactory factory = new MultipartConfigFactory();
          // 设置需要创建对象的相关信息
          factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
          factory.setMaxRequestSize(DataSize.of(15, DataUnit.MEGABYTES));
          // 通过工厂类来创建MultipartConfigElement对象
          return factory.createMultipartConfig();
      }
      ```
#### 5.2 显示头像
* 在页面中通过ajax请求来提交文件，提交完成后返回json串，解析出data中数据，设置到img头像标签的src属性上就可以了。
  * serialize():可以将表单中的数据自动拼接成key=value的结构进行提交给服务器，一般提交都是普通的控件类型中的数据(text/password/radio/checkbox)等等
  * FormData类:将表单中的数据保持原有的结构进行数据的提交。
    * ```js
      new FormData($("#form")[0]); // 文件类型的数据可以使用FormData对象进行存储
      ```
  * ajax默认处理数据时按照字符串的形式进行处理，以及默认会采用字符串的形式进行提交数据。关闭这两个默认的功能。
    * ```
      processData: false, // 处理数据的形式-false：关闭处理数据
      contentType: false, // 提交数据的形式-false：关闭默认提交数据的形式
      ```
#### 5.3 登录后显示头像
* 更新头像后，将服务器返回的头像路径保存在客户端的cookie对象中，每次检测到用户打开上传头像页面，在这个页面中通过ready()方法来自动监测去读取cookie中的头像并设置的src属性上。
  * 1-设置cookie中的值
    * ```js
      // 导入cookie.js文件
      <script src="../bootstrap3/js/jquery.cookie.js" type="text/javascript" charset="utf-8"></script>
      // 调用cookie方法：
      $.cookie(key, value, time); //单位：天
      ```
  * 2-在upload.html页面先引入cookie.js文件
    * ```js
      <script src="../bootstrap3/js/jquery.cookie.js" type="text/javascript" charset="utf-8"></script>
      ```
  * 3-在upload.html页面通过ready()自动读取cookie中的数据。
#### 5.4 显示最新的头像
* 在更改完头像后，将最新的头像地址，再次保存在cookie中，同名保存会覆盖原有cookie中的值。
```js
$.cookie("avatar", json.data, {expires: 7});
```
## 新增收货地址
### 1 新增收货地址-数据表的创建
### 2 新增收货地址-创建实体类
* 创建Address类，定义表的相关字段，采用驼峰命名方式，最后继承BaseEntity类。
### 3 新增收货地址-持久层
#### 3.1 各功能开发顺序
* 当前收货地址功能模块：列表的展示、修改、删除、设置默认、新增收货地址。开发顺序：新增收货地址-列表的展示-设置默认收货地址-删除收货地址-修改收货地址。
#### 3.2 规划需要执行的SOL语句
* 1-插入语句：
```
insert into t_address (除了aid外字段列表) values (字段值列表)
```
* 2-一个用户的收货地址规定最多只能有20条数据对应，在插入用户数据之前先做查询操作。属于收货地址逻辑控制方面的一个异常。
```mysql
select count(*) from t_address where uid=#{uid}
```
#### 3.3 接口与抽象方法
* 创建一个接口AddressMapper，在这个接口中定义上面两个SQL语句的抽象方法定义
#### 3.4 配置SQL映射
* 创建AddressMapper映射文件，在这个文件中添加抽象方法的SQL语句映射
* 在test下的mapper文件下创建AddressMapperTests的测试类
### 4 新增收货地址-业务层
#### 4.1 规划异常
* 如果用户是第一次插入用户的收货地址，规则：当用户插入的地址是第一条时，需要将当前地址作为默认的收货地址，如果查询的统计总数为0则将当前地址的is_default设置为1.查询统计结果为0不代表异常。
* 查询的结果大于20，这时候需要抛出业务控制的异常AddressCountLimitException异常。自行创建这个异常。
* 插入数据时产生未知的异常InsertException，不需要重复创建
#### 4.2 接口与抽象方法
* 创建IAddressService接口，在其中定义业务的抽象方法。
* 创建AddressServiceImpl实现类，实现接口中的抽象方法。
  * 在配置文件中定义数据
   ```
    # Spring读取配置文件中的数据:@Value("${user.address.max-count}")
    user.address.max-count=20
   ```
  * 在实现类中实现业务控制
* 测试业务层的功能是否正常。AddressServiceTests测试业务功能。
#### 4.3 实现抽象方法
### 5 新增收货地址-控制层
#### 5.1 处理异常
* 业务层抛出了收货地址总数超标的异常，在BaseController中进行处理。
#### 5.2 设计请求
```
/addresses/add_new_address
post
Address address, HttpSession session
JsonResult<Void>
```
#### 5.3 处理请求
* 在控制层创建AddressController来处理用户收货地址的请求和响应。
* * 先登录用户，再访问http://localhost:8080/addresses/add_new_address?name=tom&phone=11112341234进行测试。
### 6 新增收货地址-前端页面

## 获取省市区列表
### 1 获取省市区列表-数据库
```mysql
CREATE TABLE t_dict_district (
  id int(11) NOT NULL AUTO_INCREMENT,
  parent varchar(6) DEFAULT NULL,
  code varchar(6) DEFAULT NULL,
  name varchar(16) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
* parent表示父区域代码号，省的父代码号为+86
### 2 获取省市区列表-实体类
* 创建District实体类
### 3 获取省市区列表-持久层
* 查询语句，根据父代号进行查询。
```mysql
select * from t_dict_district where parent=#{parent} order by code ASC
```
* 抽象方法定义：DistrictMapper接口
### 4 获取省市区列表-业务层
* 创建IDistrictService接口并定义抽象方法
* 创建DistrictServiceImpl实现类，实现抽象方法。
* 单元测试。
### 5 获取省市区列表-控制层
#### 5.1 设计请求
```
/districts/
GET
String parent
JsonResult<List<District>>
```
#### 5.2 处理请求
* 创建DistrictController类，在类中编写处理请求的方法。
* 该请求添加到白名单中
* 直接请求服务器，访问localhost:8080/districts?parent=86/，进行测试
### 6 获取省市区列表-前端页面
* 注释掉通过js来完成省市区列表加载的js代码。
```
<!--
<script type="text/javascript" src="../js/distpicker.data.js"></script>
<script type="text/javascript" src="../js/distpicker.js"></script>
-->
```
* 检查前端页面在提交省市区数据时是否有相关的name属性和id属性。
* 运行前端页面看是否可以正常保存数据(除省市区之外)。
## 获取省市区的名称
### 1 获取省市区的名称-持久层
* 规划根据当前的code来获取当前省市区的名称，对应查询SQL语句。
```mysql
select * from t_dict_district where code=#{code};
```
* 在DistrictMapper接口中定义出来。
* 在DistrictMapper.xml文件中添加抽象方法的映射
* 单元测试
### 2 获取省市区的名称-业务层
* 在业务层中没有异常需要处理。
* 定义对应的业务层接口中的抽象方法。
* 在实现类中进行实现。
* 测试可以省略不写(超过8行代码进行独立测试)
### 3 获取省市区的名称-业务层优化
* 添加地址依赖于IDistrictService业务层。
* 在addNewAddress方法中将districtService接口中获取到的省市区数据转移到address对象中，该对象包含所有用户收货地址的数据。
### 4 获取省市区的名称-前端页面
* addAddress.html页面中编写对应的省市区展示及根据用户的不同选择来显示对应标签中的内容。
* 编写相关事件的代码
## 收货地址列表展示
### 1 收货地址列表展示-持久层
#### 1.1 规划SQL语句
* 数据库数据的查询操作
```mysql
select * from t_address where uid=#{uid} order by is_default DESC, created_time DESC
```
#### 1.2 接口和抽象方法
```
/**
 * 根据用户id查询用户收货地址数据
 * @param uid 用户id
 * @return 收货地址数据
 */
List<Address> findByUid(Integer uid);
```
#### 1.3 在xml文件中添加SQL语句映射
#### 1.4 单元测试
### 2 收货地址列表展示-业务层
* 此处不用抛出相关异常，无需进行异常的设计。
* 设计业务层的接口和抽象方法
```
List<Address> getByUid(Integer uid);
```
* 在实现类中实现此方法的逻辑
* 单元测试-暂时省略
### 3 收货地址列表展示-控制层
* 请求设计
```
/addresses
HttpSession session
GET
JsonResult<List<Address>>
```
* 实现请求方法的编写
* 先登录，再访问请求的地址进行测试
### 4 收货地址列表展示-前端页面
* 在address.html页面中编写用户收货地址数据的展示列表。

## 设置默认收货地址 
### 1 设置默认收货地址-持久层
#### 1.1 规划SQL语句
* 检测当前用户设置为默认收货地址的这条数据是否存在
```mysql
select * from t_address where aid=#{aid}
```
* 在修改用户默认收货地址之前，先将所有的收货地址设置为非默认。
```mysql
update t_address set is_default=0 where uid=#{uid}
```
* 将用户当前选中的记录设置为默认收货地址
```mysql
update t_address set is_default=1, modified_user=#{modeifiedUser}, modified_time=#{modifiedTime} where aid=#{aid}
```
#### 1.2 设计抽象方法
* 在AddressMapper接口中来进行定义和声明。
#### 1.3 配置SQL映射
* 在AddressMapper.xml文件中进行配置
* 单元测试
### 2 设置默认收货地址-业务层
#### 2.1 异常规划
* 执行更新时产生未知的UpdateException异常。已经创建过了，无需重复编写
* 访问的数据不是当前登录用户的收货地址数据，非法访问：AccessDeniedException。
* 收货地址有可能不存在的异常：AddressNotFoundException异常。
#### 2.2 抽象方法的设计
* 在IAddressService接口中编写抽象方法
#### 2.3 实现抽象方法
* 在AddressServiceImpl类中进行开发和业务设计
* 单元测试
### 3 设置默认收货地址-控制层
#### 3.1 处理异常
* 在BaseController类中进行统一的处理。
#### 3.2 设计请求
```
/addresses/{aid}/set_default
@PathVariable("aid") Integer aid, HttpSession session
GET
JsonResult<Void>
```
#### 3.3 完成请求方法
* 在AddressController类中编写请求处理方法。
* 先登录再访问一个请求路径：localhost:8080/addresses/{aid}/set_default
### 4 设置默认收货地址-前端页面 
* 给设置默认收货地址按钮添加一个onclick属性，指向一个方法的调用，在这个方法中来完成ajax请求的方法。
* ```
  <td><a onclick="setDefault(#{aid}})" class="btn btn-xs add-def btn-default">设为默认</a></td>
  ```
* 在address.html页面点击"设置默认"按钮，来发送ajax请求。完成setDefault()方法的声明和定义。
* 先登录再访问address.html页面进行测试

## 删除收货地址
### 1 删除收货地址-持久层
#### 1.1 规划SQL语句
* 在删除之前判断该数据是否存在，判断该地址数据的归属是否属于当前的用户。不用重复开发
* 执行删除收货地址的信息
```mysql
delete from t_address where aid=#{aid}
``` 
* 如果用户删除的是默认收货地址，将剩下的地址中某一条设置为默认收货地址。规则可以自定义：取最新修改的收货地址作为默认收货地址(modified_time)
```mysql
select * from t_address where uid=#{uid} order by modified_time DESC limit 0,1
``` 
* 如果用户只有一条收货地址数据，删除后其他操作不用进行
#### 1.2 设计抽象方法
* 在AddressMapper接口中进行抽象方法的设计
#### 1.3 映射SQL语句
* 在AddressMapper.xml文件中进行映射。
* 单元测试
### 2 删除收货地址-业务层
#### 2.1 规划异常
* 在执行删除时可能会产生位置的删除异常，导致数据不能删除，则抛出DeleteException异常。
#### 2.2 抽象方法设计
* 在IAddressService接口中进行设计抽象方法。
#### 2.3 实现抽象方法
* 业务层方法设计和实现
* 单元测试
### 3 删除收货地址-控制层
* 处理异常DeleteException类。
* 设计请求处理：
```
/addresses/{aid}/delete
POST
Integer aid, HttpSession session
JsonResult<Void>
```
* 编写请求处理方法的实现
### 4 删除收货地址-前端页面
* 在address.html页面中添加删除按钮的事件。
```html
'<td><a onclick="delete(#{aid}})" class="btn btn-xs add-del btn-info"><span class="fa fa-trash-o"></span> 删除</a></td>
```
* 编写deleteByAid(aid)方法的实现。
* 登录系统，再访问收货地址页面进行删除的数据测试。

## 商品热销排行
### 1 商品热销排行-数据表的创建
### 2 商品热销排行-创建实体类
### 3 商品热销排行-持久层
#### 3.1 规划SQL语句
* 查询热销商品列表：
```mysql
select * from t_product where status=1 order by priority desc limit 0,4
```
#### 3.2 设计抽象方法
#### 3.3 配置SQL映射
### 4 商品热销排行-业务层
#### 4.1 规划异常
* 无异常
#### 4.2 接口与抽象方法
* 创建IProductService接口
#### 4.3 实现抽象方法
* 创建ProductServiceImpl实现类
### 5 商品热销排行-控制层
#### 5.1 处理异常
* 无异常
#### 5.2 设计请求
* 设计用户提交的请求，并设计响应方式
```
请求路径：/products/hot_list
请求参数：无
请求方式：GET
响应结果：JsonResult<List<Product>>
是否拦截：否，需要将index.html和products/**添加到白名单
```
#### 5.3 处理请求
* 创建ProductController
* 在类中添加处理请求的getHotList()方法
### 6 商品热销排行-前端页面

## 显示商品详情
### 1 商品-显示商品详情-持久层
#### 1.1 规划需要执行的SQL语句
* 根据商品id显示商品详情的SQL语句大致是。
```mysql
SELECT * FROM t_product WHERE id=#{id}
```
#### 1.2 接口与抽象方法
* 在ProductMapper接口中添加抽象方法。
```
/**
 * 根据商品id查询商品详情
 * @param id 商品id
 * @return 匹配的商品详情，如果没有匹配的数据则返回null
 */
Product findById(Integer id);
```
#### 1.3 配置SQL映射
* 在ProductMapper.xml文件中配置findById(Integer id)方法的映射。
```xml
<!-- 根据商品id查询商品详情：Product findById(Integer id) -->
<select id="findById" resultMap="ProductEntityMap">
    SELECT * FROM t_product WHERE id=#{id}
</select>
```
* 在ProductMapperTests测试类中添加测试方法。
```
@Test
public void findById() {
    Integer id = 10000017;
    Product result = productMapper.findById(id);
    System.out.println(result);
}
```
### 2 商品-显示商品详情-业务层
#### 2.1 规划异常
* 如果商品数据不存在，应该抛出ProductNotFoundException，需要创建com.cy.store.service.ex.ProductNotFoundException异常。
```java
package com.cy.store.service.ex;
/** 商品数据不存在的异常 */
public class ProductNotFoundException extends ServiceException {
    // Override Methods...
}
```
#### 2.2 接口与抽象方法
* 在业务层IProductService接口中添加findById(Integer id)抽象方法。
```
/**
 * 根据商品id查询商品详情
 * @param id 商品id
 * @return 匹配的商品详情，如果没有匹配的数据则返回null
 */
Product findById(Integer id);
```
#### 2.3 实现抽象方法
* 在ProductServiceImpl类中，实现接口中的findById(Integer id)抽象方法。
```
@Override
public Product findById(Integer id) {
    // 根据参数id调用私有方法执行查询，获取商品数据
    Product product = productMapper.findById(id);
    // 判断查询结果是否为null
    if (product == null) {
        // 是：抛出ProductNotFoundException
        throw new ProductNotFoundException("尝试访问的商品数据不存在");
    }
    // 将查询结果中的部分属性设置为null
    product.setPriority(null);
    product.setCreatedUser(null);
    product.setCreatedTime(null);
    product.setModifiedUser(null);
    product.setModifiedTime(null);
    // 返回查询结果
    return product;
}
```
* 在ProductServiceTests测试类中编写测试方法。
```
@Test
public void findById() {
    try {
        Integer id = 100000179;
        Product result = productService.findById(id);
        System.out.println(result);
    } catch (ServiceException e) {
        System.out.println(e.getClass().getSimpleName());
        System.out.println(e.getMessage());
    }
}
```
### 3 商品-显示商品详情-控制器
#### 3.1 处理异常
* 在BaseController类中的handleException()方法中添加处理ProductNotFoundException的异常。
```
// ...
else if (e instanceof ProductNotFoundException) {
	result.setState(4006);
}
// ...
```
#### 3.2  设计请求
* 设计用户提交的请求，并设计响应的方式。
```
请求路径：/products/{id}/details
请求参数：@PathVariable("id") Integer id
请求类型：GET
响应结果：JsonResult<Product>
```
#### 3.3 处理请求
* 在ProductController类中添加处理请求的getById()方法。
```
@GetMapping("{id}/details")
public JsonResult<Product> getById(@PathVariable("id") Integer id) {
    // 调用业务对象执行获取数据
    Product data = productService.findById(id);
    // 返回成功和数据
    return new JsonResult<Product>(OK, data);
}
```
* 完成后启动项目，直接访问http://localhost:8080/products/10000017/details进行测试。
### 4 商品-显示商品详情-前端页面
* 检查在product.html页面body标签内部的最后是否引入jquery-getUrlParam.js文件，如果引入无需重复引入。
```
<script type="text/javascript" src="../js/jquery-getUrlParam.js"></script>
```
* 在product.html页面中body标签内部的最后添加获取当前商品详情的代码。
```
<script type="text/javascript">
let id = $.getUrlParam("id");
console.log("id=" + id);
$(document).ready(function() {
    $.ajax({
        url: "/products/" + id + "/details",
        type: "GET",
        dataType: "JSON",
        success: function(json) {
            if (json.state == 200) {
                console.log("title=" + json.data.title);
                $("#product-title").html(json.data.title);
                $("#product-sell-point").html(json.data.sellPoint);
                $("#product-price").html(json.data.price);

                for (let i = 1; i <= 5; i++) {
                    $("#product-image-" + i + "-big").attr("src", ".." + json.data.image + i + "_big.png");
                    $("#product-image-" + i).attr("src", ".." + json.data.image + i + ".jpg");
                }
            } else if (json.state == 4006) { // 商品数据不存在的异常
                location.href = "index.html";
            } else {
                alert("获取商品信息失败！" + json.message);
            }
        }
    });
});
</script>
```
* 完成后启动项目，先访问http://localhost:8080/web/index.html页面，然后点击“热销排行”中的某个子项，将跳转到product.html商品详情页，观察页面是否加载的是当前的商品信息。

## 加入购物车
### 1 购物车-创建数据表
* 使用use命令先选中store数据库。
```mysql
USE store;
```
* 在store数据库中创建t_cart用户数据表。
```mysql
CREATE TABLE t_cart (
	cid INT AUTO_INCREMENT COMMENT '购物车数据id',
	uid INT NOT NULL COMMENT '用户id',
	pid INT NOT NULL COMMENT '商品id',
	price BIGINT COMMENT '加入时商品单价',
	num INT COMMENT '商品数量',
	created_user VARCHAR(20) COMMENT '创建人',
	created_time DATETIME COMMENT '创建时间',
	modified_user VARCHAR(20) COMMENT '修改人',
	modified_time DATETIME COMMENT '修改时间',
	PRIMARY KEY (cid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
### 2 购物车-创建实体类
* 在com.cy.store.entity包下创建购物车的Cart实体类。
```java
package com.cy.store.entity;
import java.io.Serializable;

/** 购物车数据的实体类 */
public class Cart extends BaseEntity implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;

    // Generate: Getter and Setter、Generate hashCode() and equals()、toString()
}
```
### 3 购物车-添加购物车-持久层
#### 3.1 规划需要执行的SQL语句
* 向购物车表中插入商品数据的SQL语句大致是：
```
insert into t_cart (除了cid以外的字段列表) values (匹配的值列表);
```
* 如果用户曾经将某个商品加入到购物车过，则点击“加入购物车”按钮只会对购物车中相同商品数量做递增操作。
```mysql
update t_cart set num=#{num} where cid=#{cid}
```
* 关于判断“到底应该插入数据，还是修改数量”，可以通过“查询某用户是否已经添加某商品到购物车”来完成。如果查询到某结果，就表示该用户已经将该商品加入到购物车了，如果查询结果为null，则表示该用户没有添加过该商品。
```mysql
select * from t_cart where uid=#{uid} and pid=#{pid}
```
#### 3.2 接口与抽象方法
* 在com.cy.store.mapper包下创建CartMapper接口，并添加抽象相关的方法。
```java
package com.cy.store.mapper;
import com.cy.store.entity.Cart;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
/** 处理购物车数据的持久层接口 */
public interface CartMapper {
    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);
    /**
     * 修改购物车数据中商品的数量
     * @param cid 购物车数据的id
     * @param num 新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);
    /**
     * 根据用户id和商品id查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 匹配的购物车数据，如果该用户的购物车中并没有该商品，则返回null
     */
    Cart findByUidAndPid(
            @Param("uid") Integer uid,
            @Param("pid") Integer pid);
}
```
#### 3.3 配置SQL映射
* 在resources.mapper文件夹下创建CartMapper.xml文件，并在文件中配置以上三个方法的映射。
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cy.store.mapper.CartMapper">
    <resultMap id="CartEntityMap" type="com.cy.store.entity.Cart">
        <id column="cid" property="cid"/>
        <result column="created_user" property="createdUser"/>
        <result column="created_time" property="createdTime"/>
        <result column="modified_user" property="modifiedUser"/>
        <result column="modified_time" property="modifiedTime"/>
    </resultMap>
  
    <!-- 插入购物车数据：Integer insert(Cart cart) -->
    <insert id="insert" useGeneratedKeys="true" keyProperty="cid">
        INSERT INTO t_cart (uid, pid, price, num, created_user, created_time, modified_user, modified_time)
        VALUES (#{uid}, #{pid}, #{price}, #{num}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})
    </insert>

    <!-- 修改购物车数据中商品的数量：
         Integer updateNumByCid(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime) -->
    <update id="updateNumByCid">
        UPDATE
            t_cart
        SET
            num=#{num},
            modified_user=#{modifiedUser},
            modified_time=#{modifiedTime}
        WHERE
            cid=#{cid}
    </update>

    <!-- 根据用户id和商品id查询购物车中的数据：
         Cart findByUidAndPid(
            @Param("uid") Integer uid,
            @Param("pid") Integer pid) -->
    <select id="findByUidAndPid" resultMap="CartEntityMap">
        SELECT
            *
        FROM
            t_cart
        WHERE
            uid=#{uid} AND pid=#{pid}
    </select>
</mapper>
```
* 在com.cy.store.mapper包下创建CartMapperTests测试类，并添加测试方法。
```java
package com.cy.store.mapper;
import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(1);
        cart.setPid(2);
        cart.setNum(3);
        cart.setPrice(4L);
        Integer rows = cartMapper.insert(cart);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateNumByCid() {
        Integer cid = 1;
        Integer num = 10;
        String modifiedUser = "购物车管理员";
        Date modifiedTime = new Date();
        Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Integer uid = 1;
        Integer pid = 2;
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        System.out.println(result);
    }
}
```
### 4 购物车-添加购物车-业务层
#### 4.1 规划异常
* 在插入数据时，可能抛出InsertException异常；在修改数据时，可能抛出UpdateException异常。如果不限制购物车中的记录的数量，则没有其它异常。
#### 4.2 接口与抽象方法
* 在com.cy.store.service包下创建ICartService接口，并添加抽象方法。
```java
package com.cy.store.service;

/** 处理商品数据的业务层接口 */
public interface ICartService {
    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户的id
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);
}
```
#### 4.3 实现抽象方法
* 创建com.cy.store.service.impl.CartServiceImpl类，并实现ICartService接口，并在类的定义前添加@Service注解。在类中声明CartMapper持久层对象和IProductService处理商品数据的业务对象，并都添加@Autowired注修饰。
```java
package com.cy.store.service.impl;
import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/** 处理购物车数据的业务层实现类 */
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private IProductService productService;
}
```
* 在CartServiceImpl类中实现业务层ICartService接口中定义的抽象方法。
```
@Override
public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
    // 根据参数pid和uid查询购物车中的数据
    // 判断查询结果是否为null
    // 是：表示该用户并未将该商品添加到购物车
    // -- 创建Cart对象
    // -- 封装数据：uid,pid,amount
    // -- 调用productService.findById(pid)查询商品数据，得到商品价格
    // -- 封装数据：price
    // -- 封装数据：4个日志
    // -- 调用insert(cart)执行将数据插入到数据表中
    // 否：表示该用户的购物车中已有该商品
    // -- 从查询结果中获取购物车数据的id
    // -- 从查询结果中取出原数量，与参数amount相加，得到新的数量
    // -- 执行更新数量
}
```
* addToCart(Integer uid, Integer pid, Integer amount, String username)方法的代码具体实现。
```
@Override
public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
    // 根据参数pid和uid查询购物车中的数据
    Cart result = cartMapper.findByUidAndPid(uid, pid);
    Integer cid = result.getCid();
    Date now = new Date();
    // 判断查询结果是否为null
    if (result == null) {
        // 是：表示该用户并未将该商品添加到购物车
        // 创建Cart对象
        Cart cart = new Cart();
        // 封装数据：uid,pid,amount
        cart.setUid(uid);
        cart.setPid(pid);
        cart.setNum(amount);
        // 调用productService.findById(pid)查询商品数据，得到商品价格
        Product product = productService.findById(pid);
        // 封装数据：price
        cart.setPrice(product.getPrice());
        // 封装数据：4个日志
        cart.setCreatedUser(username);
        cart.setCreatedTime(now);
        cart.setModifiedUser(username);
        cart.setModifiedTime(now);
        // 调用insert(cart)执行将数据插入到数据表中
        Integer rows = cartMapper.insert(cart);
        if (rows != 1) {
            throw new InsertException("插入商品数据时出现未知错误，请联系系统管理员");
        }
    } else {
        // 否：表示该用户的购物车中已有该商品
        // 从查询结果中获取购物车数据的id
        Integer cid = result.getCid();
        // 从查询结果中取出原数量，与参数amount相加，得到新的数量
        Integer num = result.getNum() + amount;
        // 执行更新数量
        Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
        if (rows != 1) {
            throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
        }
    }
}
```
* 在com.cy.store.service包下创建测试类CartServiceTests类，并编写测试方法。
```java
package com.cy.store.service;
import com.cy.store.entity.Product;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {
    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        try {
            Integer uid = 2;
            Integer pid = 10000007;
            Integer amount = 1;
            String username = "Tom";
            cartService.addToCart(uid, pid, amount, username);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
```
### 5 购物车-添加购物车-控制层
#### 5.1 处理异常
> **说明**：无异常。
#### 5.2 设计请求
* 设计用户提交的请求，并设计响应的方式。
```
请求路径：/carts/add_to_cart
请求参数：Integer pid, Integer amount, HttpSession session
请求类型：POST
响应结果：JsonResult<Void>
```
#### 5.3 处理请求

* 在com.cy.store.controller包下创建CartController类并继承自BaseController类，添加@RequestMapping("carts")和@RestController注解；在类中声明ICartService业务对象，并使用@Autowired注解修饰。
```java
package com.cy.store.controller;
import com.cy.store.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {
    @Autowired
    private ICartService cartService;

}
```
* 在CartController类中添加处理请求的addToCart()方法。
```
@RequestMapping("add_to_cart")
public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
    // 从Session中获取uid和username
    Integer uid = getUidFromSession(session);
    String username = getUsernameFromSession(session);
    // 调用业务对象执行添加到购物车
    cartService.addToCart(uid, pid, amount, username);
    // 返回成功
    return new JsonResult<Void>(OK);
}
```
* 完成后启动项目，先登录再访问http://localhost:8080/carts/add_to_cart?pid=10000017&amount=3进行测试。
### 6 购物车-添加购物车-前端页面
* 在product.html页面中的body标签内的script标签里为“加入购物车”按钮添加点击事件。
```javascript
$("#btn-add-to-cart").click(function() {
    $.ajax({
        url: "/carts/add_to_cart",
        type: "POST",
        data: {
            "pid": id,
            "amount": $("#num").val()
        },
        dataType: "JSON",
        success: function(json) {
            if (json.state == 200) {
                alert("增加成功！");
            } else {
                alert("增加失败！" + json.message);
            }
        },
        error: function(xhr) {
            alert("您的登录信息已经过期，请重新登录！HTTP响应码：" + xhr.status);
            location.href = "login.html";
        }
    });
});
```
> $.ajax函数中参数data提交请参数的方式：
>
> ```
> // 1.适用于参数较多，且都在同一个表单中
> data: $("#form表单id属性值").serialize()
> // 2.仅适用于上传文件
> data: new FormData($("##form表单id属性值")[0])
> // 3.参数拼接形式提交
> data: "pid=10000005&amount=3"
> // 4.使用JSON格式提交参数
> data: {
>    	"pid": 10000005,
>    	"amount": 3
> }
> ```
* 完成后启动项目，先登录再访问http://localhost:8080/web/index.html页面进行测试。

## 显示购物车列表
### 1 购物车-显示列表-持久层
#### 1.1 规划需要执行的SQL语句
* 显示某用户的购物车列表数据的SQL语句大致是。
```mysql
SELECT
	cid,
	uid,
	pid,
	t_cart.price,
	t_cart.num,
	t_product.title,
	t_product.price AS realPrice,
	t_product.image
FROM
	t_cart
	LEFT JOIN t_product ON t_cart.pid = t_product.id 
WHERE
	uid = #{uid}
ORDER BY
	t_cart.created_time DESC
```
#### 1.2 接口与抽象方法
* 由于涉及多表关联查询，必然没有哪个实体类可以封装此次的查询结果，因此需要创建VO类。创建com.cy.store.vo.CartVO类。
* VO:Value Object-值对象，当进行select查询时，查询的结果属于多张表中的内容，结果集不能直接使用pojo实体类来接收，pojo实体类不能包含多表查询的结果。解决方式是重新构建一个新的对象，用于存储查询出来的结果集对应的映射，把这种对象称为值对象。
```java
package com.cy.store.vo;
import java.io.Serializable;

/** 购物车数据的Value Object类 */
public class CartVO implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private Long realPrice;
    private String image;
    
    // Generate: Getter and Setter、Generate hashCode() and equals()、toString()
}
```
* 在CartMapper接口中添加抽象方法。
```
/**
 * 查询某用户的购物车数据
 * @param uid 用户id
 * @return 该用户的购物车数据的列表
 */
List<CartVO> findVOByUid(Integer uid);
```
#### 1.3 配置SQL映射
* 在CartMapper.xml文件中添加findVOByUid()方法的映射。
```xml
<!-- 查询某用户的购物车数据：List<CartVO> findVOByUid(Integer uid) -->
<select id="findVOByUid" resultType="com.cy.store.vo.CartVO">
    SELECT
        cid,
        uid,
        pid,
        t_cart.price,
        t_cart.num,
        t_product.title,
        t_product.price AS realPrice,
        t_product.image
    FROM
        t_cart
        LEFT JOIN t_product ON t_cart.pid = t_product.id 
    WHERE
        uid = #{uid}
    ORDER BY
        t_cart.created_time DESC
</select>
```
2.在CartMapperTests测试类中添加findVOByUid()方法的测试。
```
@Test
public void findVOByUid() {
    List<CartVO> list = cartMapper.findVOByUid(31);
    System.out.println(list);
}
```
### 2 购物车-显示列表-业务层
#### 2.1 规划异常
> **说明**：无异常。
#### 2.2 接口与抽象方法
* 在ICartService接口中添加findVOByUid()抽象方法。
```
/**
 * 查询某用户的购物车数据
 * @param uid 用户id
 * @return 该用户的购物车数据的列表
 */
List<CartVO> getVOByUid(Integer uid);
```
#### 2.3 实现抽象方法
* 在CartServiceImpl类中重写业务接口中的抽象方法。
```
@Override
public List<CartVO> getVOByUid(Integer uid) {
    return cartMapper.findVOByUid(uid);
}
```
* 在CartServiceTests测试类中添加getVOByUid()测试方法。
```
@Test
public void getVOByUid() {
    List<CartVO> list = cartService.getVOByUid(31);
    System.out.println("count=" + list.size());
    for (CartVO item : list) {
        System.out.println(item);
    }
}
```
### 3 购物车-显示列表-控制器
#### 3.1 处理异常
> **说明**：无异常。
#### 3.2 设计请求
* 设计用户提交的请求，并设计响应的方式。
```
请求路径：/carts/
请求参数：HttpSession session
请求类型：GET
响应结果：JsonResult<List<CartVO>>
```
#### 3.3 处理请求
* 在CartController类中编写处理请求的代码。
```
@GetMapping({"", "/"})
public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
    // 从Session中获取uid
    Integer uid = getUidFromSession(session);
    // 调用业务对象执行查询数据
    List<CartVO> data = cartService.getVOByUid(uid);
    // 返回成功与数据
    return new JsonResult<List<CartVO>>(OK, data);
}
```
* 完成后启动项目，先登录再访问http://localhost:8080/carts请求进行测试。
### 4 购物车-显示列表-前端页面
* 将cart.html页面的head头标签内引入的cart.js文件注释掉。
```
<!-- <script src="../js/cart.js" type="text/javascript" charset="utf-8"></script> -->
```
* 给form标签添加action="orderConfirm.html"属性、tbody标签添加id="cart-list"属性、结算按钮的类型改为type="button"值。如果以上属性值已经添加过无需重复添加。
* 在cart.html页面body标签内的script标签中编写展示购物车列表的代码。
```js
$(document).ready(function() {
    showCartList();
});

function showCartList() {
    $("#cart-list").empty();
    $.ajax({
        url: "/carts",
        type: "GET",
        dataType: "JSON",
        success: function(json) {
            let list = json.data;
            for (let i = 0; i < list.length; i++) {
                let tr = '<tr>'
                + '<td>'
                + 	'<input name="cids" value="#{cid}" type="checkbox" class="ckitem" />'
                + '</td>'
                + '<td><img src="..#{image}collect.png" class="img-responsive" /></td>'
                + '<td>#{title}#{msg}</td>'
                + '<td>¥<span id="price-#{cid}">#{realPrice}</span></td>'
                + '<td>'
                + 	'<input type="button" value="-" class="num-btn" onclick="reduceNum(1)" />'
                + 	'<input id="num-#{cid}" type="text" size="2" readonly="readonly" class="num-text" value="#{num}">'
                + 	'<input class="num-btn" type="button" value="+" onclick="addNum(#{cid})" />'
                + '</td>'
                + '<td>¥<span id="total-price-#{cid}">#{totalPrice}</span></td>'
                + '<td>'
                + 	'<input type="button" onclick="delCartItem(this)" class="cart-del btn btn-default btn-xs" value="删除" />'
                + '</td>'
                + '</tr>';
                tr = tr.replace(/#{cid}/g, list[i].cid);
                tr = tr.replace(/#{title}/g, list[i].title);
                tr = tr.replace(/#{image}/g, list[i].image);
                tr = tr.replace(/#{realPrice}/g, list[i].realPrice);
                tr = tr.replace(/#{num}/g, list[i].num);
                tr = tr.replace(/#{totalPrice}/g, list[i].realPrice * list[i].num);

                if (list[i].realPrice < list[i].price) {
                    tr = tr.replace(/#{msg}/g, "比加入时降价" + (list[i].price - list[i].realPrice) + "元");
                } else {
                    tr = tr.replace(/#{msg}/g, "");
                }
                $("#cart-list").append(tr);
            }
        }
    });
}
```
* 完成后启动项目，先登录再访问http://localhost:8080/web/cart.html页面进行测试。
## 增加商品数量
### 1 购物车-增加商品数量-持久层
#### 1.1 规划需要执行的SQL语句
* 首先进行查询需要操作的购物车数据信息。
```mysql
SELECT * FROM t_cart WHERE cid=#{cid}
```
* 然后计算出新的商品数量值，如果满足更新条件则执行更新操作。此SQL语句无需重复开发。
```mysql
UPDATE t_cart SET num=#{num}, modified_user=#{modifiedUer}, modified_time=#{modifiedTime} WHERE cid=#{cid}
```
#### 1.2 接口与抽象方法
* 在CartMapper接口中添加抽象方法。
```
/**
 * 根据购物车数据id查询购物车数据详情
 * @param cid 购物车数据id
 * @return 匹配的购物车数据详情，如果没有匹配的数据则返回null
 */
Cart findByCid(Integer cid);
```
#### 1.3 配置SQL映射
* 在CartMapper文件中添加findByCid(Integer cid)方法的映射。
```xml
<!-- 根据购物车数据id查询购物车数据详情：Cart findByCid(Integer cid) -->
<select id="findByCid" resultMap="CartEntityMap">
    SELECT
   		*
    FROM
    	t_cart
    WHERE
    	cid = #{cid}
</select>
```
* 在CartMapperTests测试类中添加findByCid()测试方法。
```
@Test
public void findByCid() {
	Integer cid = 6;
	Cart result = cartMapper.findByCid(cid);
	System.out.println(result);
}
```
### 2 购物车-增加商品数量-业务层
#### 2.1 规划异常
* 如果尝试访问的购物车数据不存在，则抛出CartNotFoundException异常。创建com.cy.store.service.ex.CartNotFoundException类。
```java
/** 购物车数据不存在的异常 */
public class CartNotFoundException extends ServiceException {
	// Override Methods...
}
```
* 如果尝试访问的数据并不是当前登录用户的数据，则抛出AccessDeniedException异常。此异常类无需再次创建。
* 最终执行更新操作时，可能会抛出UpdateException异常。此异常类无需再次创建。
#### 2.2 接口与抽象方法
* 在业务层ICartService接口中添加addNum()抽象方法。
```
/**
 * 将购物车中某商品的数量加1
 * @param cid 购物车数量的id
 * @param uid 当前登录的用户的id
 * @param username 当前登录的用户名
 * @return 增加成功后新的数量
 */
Integer addNum(Integer cid, Integer uid, String username);
```
#### 2.3 实现抽象方法
* 在CartServiceImpl类中，实现接口中的抽象方法并规划业务逻辑。
```
public Integer addNum(Integer cid, Integer uid, String username) {
	// 调用findByCid(cid)根据参数cid查询购物车数据
	// 判断查询结果是否为null
	// 是：抛出CartNotFoundException

	// 判断查询结果中的uid与参数uid是否不一致
	// 是：抛出AccessDeniedException

	// 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
	// 根据查询结果中的原数量增加1得到新的数量num

	// 创建当前时间对象，作为modifiedTime
	// 调用updateNumByCid(cid, num, modifiedUser, modifiedTime)执行修改数量
}
```
* 实现addNum()方法中的业务逻辑代码。
```
@Override
public Integer addNum(Integer cid, Integer uid, String username) {
    // 调用findByCid(cid)根据参数cid查询购物车数据
    Cart result = cartMapper.findByCid(cid);
    // 判断查询结果是否为null
    if (result == null) {
        // 是：抛出CartNotFoundException
        throw new CartNotFoundException("尝试访问的购物车数据不存在");
    }

    // 判断查询结果中的uid与参数uid是否不一致
    if (!result.getUid().equals(uid)) {
        // 是：抛出AccessDeniedException
        throw new AccessDeniedException("非法访问");
    }

    // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
    // 根据查询结果中的原数量增加1得到新的数量num
    Integer num = result.getNum() + 1;

    // 创建当前时间对象，作为modifiedTime
    Date now = new Date();
    // 调用updateNumByCid(cid, num, modifiedUser, modifiedTime)执行修改数量
    Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
    if (rows != 1) {
        throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
    }

    // 返回新的数量
    return num;
}
```
* 在CartServiceTests测试类中添加addNum()测试方法。
```
@Test
public void addNum() {
    try {
        Integer cid = 6;
        Integer uid = 31;
        String username = "管理员";
        Integer num = cartService.addNum(cid, uid, username);
        System.out.println("OK. New num=" + num);
    } catch (ServiceException e) {
        System.out.println(e.getClass().getSimpleName());
        System.out.println(e.getMessage());
    }
}
```
### 3 购物车-增加商品数量-控制器
#### 3.1 处理异常
* 在BaseController类中添加CartNotFoundException异常类的统一管理。
```
// ...
else if (e instanceof CartNotFoundException) {
    result.setState(4007);
}
// ...
```
#### 3.2 设计请求
设计用户提交的请求，并设计响应的方式。
```
请求路径：/carts/{cid}/num/add
请求参数：@PathVariable("cid") Integer cid, HttpSession session
请求类型：POST
响应结果：JsonResult<Integer>
```
#### 3.3 处理请求
* 在CartController类中添加处理请求的addNum()方法。
```
@RequestMapping("{cid}/num/add")
public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
    // 从Session中获取uid和username
    Integer uid = getUidFromSession(session);
    String username = getUsernameFromSession(session);
    // 调用业务对象执行增加数量
    Integer data = cartService.addNum(cid, uid, username);
    // 返回成功
    return new JsonResult<Integer>(OK, data);
}
```
* 完成后启动项目，先登录再访问http://localhost:8080/carts/6/num/add页面进行测试。
### 4 购物车-增加商品数量-前端页面
* 首先确定在showCartList()函数中动态拼接的增加购物车按钮是绑定了addNum()事件，如果已经添加无需重复添加。
```javascript
<input class="num-btn" type="button" value="+" onclick="addNum(#{cid})" />
```
* 在script标签中定义addNum()函数并编写增加购物车数量的逻辑代码。
```javascript
function addNum(cid) {
    $.ajax({
        url: "/carts/" + cid + "/num/add",
        type: "POST",
        dataType: "JSON",
        success: function(json) {
            if (json.state == 200) {
                // showCartList();
                $("#num-" + cid).val(json.data);
                let price = $("#price-" + cid).html();
                let totalPrice = price * json.data;
                $("#total-price-" + cid).html(totalPrice);
            } else {
                alert("增加商品数量失败！" + json.message);
            }
        },
        error: function(xhr) {
            alert("您的登录信息已经过期，请重新登录！HTTP响应码：" + xhr.status);
            location.href = "login.html";
        }
    });
}
```
* 完成后启动项目，先登录再访问http://localhost:8080/web/cart.html页面点击“+”按钮进行测试。
## 显示勾选的购物车数据
### 1 显示确认订单页-显示勾选的购物车数据-持久层
#### 1.1 规划需要执行的SQL语句
* 在“确认订单页”显示的商品信息，应来自前序页面（购物车列表）中勾选的数据，所以显示的信息其实是购物车中的数据。到底需要显示哪些取决于用户的勾选操作，当用户勾选了若干条购物车数据后，这些数据的id应传递到当前“确认订单页”中，该页面根据这些id获取需要显示的数据列表。
* 所以在持久层需要完成“根据若干个不确定的id值，查询购物车数据表，显示购物车中的数据信息”。则需要执行的SQL语句大致是。
```mysql
SELECT
	cid,
	uid,
	pid,
	t_cart.price,
	t_cart.num,
	t_product.title,
	t_product.price AS realPrice,
	t_product.image
FROM
	t_cart
	LEFT JOIN t_product ON t_cart.pid = t_product.id 
WHERE
	cid IN (?, ?, ?)
ORDER BY
	t_cart.created_time DESC	
```
#### 1.2 接口与抽象方法
* 在CartMapper接口中添加findVOByCids(Integer[] cids)方法。
```
/**
 * 根据若干个购物车数据id查询详情的列表
 * @param cids 若干个购物车数据id
 * @return 匹配的购物车数据详情的列表
 */
List<CartVO> findVOByCids(Integer[] cids);
```
#### 1.3 配置SQL映射
* 在CartMapper.xml文件中添加SQL语句的映射配置。
```xml
<!-- 根据若干个购物车数据id查询详情的列表：List<CartVO> findVOByCids(Integer[] cids) -->
<select id="findVOByCids" resultType="com.cy.store.vo.CartVO">
    SELECT
        cid,
        uid,
        pid,
        t_cart.price,
        t_cart.num,
        t_product.title,
        t_product.price AS realPrice,
        t_product.image
    FROM
        t_cart
            LEFT JOIN t_product ON t_cart.pid = t_product.id
    WHERE
        cid IN (
            <foreach collection="array" item="cid" separator=",">
                #{cid}
            </foreach>
        )
    ORDER BY
        t_cart.created_time DESC
</select>
```
* 在CartMapperTests测试类中添加findVOByCids()测试方法。
```
@Test
public void findVOByCids() {
    Integer[] cids = {1, 2, 6, 7, 8, 9, 10};
    List<CartVO> list = cartMapper.findVOByCids(cids);
    System.out.println("count=" + list.size());
    for (CartVO item : list) {
        System.out.println(item);
    }
}
```
### 2 显示确认订单页-显示勾选的购物车数据-业务层
#### 2.1 规划异常
> **说明**：无异常。
#### 2.2 接口与抽象方法
* 在ICartService接口中添加getVOByCids()抽象方法。
```
/**
 * 根据若干个购物车数据id查询详情的列表
 * @param uid 当前登录的用户的id
 * @param cids 若干个购物车数据id
 * @return 匹配的购物车数据详情的列表
 */
List<CartVO> getVOByCids(Integer uid, Integer[] cids);
```
#### 2.3 实现抽象方法
* 在CartServiceImpl类中重写业务接口中的抽象方法。
```
@Override
public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
    List<CartVO> list = cartMapper.findVOByCids(cids);
    /**
    for (CartVO cart : list) {
		if (!cart.getUid().equals(uid)) {
			list.remove(cart);
		}
	}
	*/
    Iterator<CartVO> it = list.iterator();
    while (it.hasNext()) {
        CartVO cart = it.next();
        if (!cart.getUid().equals(uid)) {
            it.remove();
        }
    }
    return list;
}
```
* 在CartServiceTests测试类中添加getVOByCids()测试方法。
```
@Test
public void getVOByCids() {
    Integer[] cids = {1, 2, 6, 7, 8, 9, 10};
    Integer uid = 31;
    List<CartVO> list = cartService.getVOByCids(uid, cids);
    System.out.println("count=" + list.size());
    for (CartVO item : list) {
        System.out.println(item);
    }
}
```
### 3 显示确认订单页-显示勾选的购物车数据-控制器
#### 3.1 处理异常
**说明**：无异常。
#### 3.2 设计请求
* 设计用户提交的请求，并设计响应的方式。
```
请求路径：/carts/list
请求参数：Integer[] cids, HttpSession session
请求类型：GET
响应结果：JsonResult<List<CartVO>>
```
#### 3.3 处理请求
* 在CartController类中添加处理请求的getVOByCids()方法。
```
@GetMapping("list")
public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
    // 从Session中获取uid
    Integer uid = getUidFromSession(session);
    // 调用业务对象执行查询数据
    List<CartVO> data = cartService.getVOByCids(uid, cids);
    // 返回成功与数据
    return new JsonResult<>(OK, data);
}
```
* 完成后启动项目，先登录再访问http://localhost:8080/carts/list?cids=7&cids=8&cids=13&cids=14&cids=17地址进行测试。
### 4 显示确认订单页-前端页面
#### 4.1 显示勾选的购物车数据-前端页面
* 在orderConfirm.html页面的head标签里注释掉引入外部的orderConfirm.js文件。
```
<!-- <script src="../js/orderConfirm.js" type="text/javascript" charset="utf-8"></script> -->
```
* 在orderConfirm.html页面中检查必要控件的属性是否添加，如果已添加无需重复添加。
* 在orderConfirm.html页面中的body标签内的最后添加srcipt标签并在标签内部添加处理购物车“订单商品信息”列表展示的代码。
```
<script type="text/javascript">
$(document).ready(function() {
    // showAddressList();
    showCartList();
});

function showCartList() {
    $("#cart-list").empty();
    $.ajax({
        url: "/carts/list",
        data: location.search.substr(1),
        type: "GET",
        dataType: "JSON",
        success: function(json) {
            let list = json.data;
            console.log("count=" + list.length);
            let allCount = 0;
            let allPrice = 0;
            for (let i = 0; i < list.length; i++) {
                console.log(list[i].title);
                let tr = '<tr>'
                + '<td><img src="..#{image}collect.png" class="img-responsive" /></td>'
                + '<td><input type="hidden" name="cids" value="#{cid}" />#{title}</td>'
                + '<td>¥<span>#{realPrice}</span></td>'
                + '<td>#{num}</td>'
                + '<td>¥<span>#{totalPrice}</span></td>'
                + '</tr>';

                tr = tr.replace(/#{cid}/g, list[i].cid);
                tr = tr.replace(/#{image}/g, list[i].image);
                tr = tr.replace(/#{title}/g, list[i].title);
                tr = tr.replace(/#{realPrice}/g, list[i].realPrice);
                tr = tr.replace(/#{num}/g, list[i].num);
                tr = tr.replace(/#{totalPrice}/g, list[i].realPrice * list[i].num);

                $("#cart-list").append(tr);

                allCount += list[i].num;
                allPrice += list[i].realPrice * list[i].num;
            }
            $("#all-count").html(allCount);
            $("#all-price").html(allPrice);
        }
    });
}
</script>
```
* 完成后启动项目，先登录再访问http://localhost:8080/web/cart.html页面，勾选商品再点击“结算”按钮进行测试。
#### 4.2 显示选择收货地址-前端页面
* 在orderConfirm.html页面中的body标签内的srcipt标签中添加获取收货地址列表方法的定义。
```javascript
function showAddressList() {
    $("#address-list").empty();
    $.ajax({
        url: "/addresses",
        type: "GET",
        dataType: "JSON",
        success: function(json) {
            let list = json.data;
            console.log("count=" + list.length);
            for (let i = 0; i < list.length; i++) {
                console.log(list[i].name);
                let opt = '<option value="#{aid}">#{name} | #{tag} | #{province}#{city}#{area}#{address} | #{phone}</option>';

                opt = opt.replace(/#{aid}/g, list[i].aid);
                opt = opt.replace(/#{tag}/g, list[i].tag);
                opt = opt.replace("#{name}", list[i].name);
                opt = opt.replace("#{province}", list[i].provinceName);
                opt = opt.replace("#{city}", list[i].cityName);
                opt = opt.replace("#{area}", list[i].areaName);
                opt = opt.replace("#{address}", list[i].address);
                opt = opt.replace("#{phone}", list[i].phone);

                $("#address-list").append(opt);
            }
        }
    });
}
```
* 在orderConfirm.html页面中的body标签内的srcipt标签中添加展示收货地址列表方法的调用。
```
<script type="text/javascript">
    $(document).ready(function() {
        showAddressList();
        showCartList();
    });
</script>
```
* 完成后启动项目，先登录再访问http://localhost:8080/web/orderConfirm.html页面进行测试。