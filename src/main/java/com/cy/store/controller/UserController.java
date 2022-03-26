package com.cy.store.controller;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/21 20:50
 */

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/** 处理用户相关请求的控制器类 */
@RestController // 等效于 @Controller+@ResponseBody的功能
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 约定大于配置：开发思想来完成，省略大量的配置甚至注解的编写
     *1.接收数据方式：请求处理方法的参数列表设置成pojo类型来接收前端数据
     * SpringBoot会将前端的url地址中的参数名和pojo类的属性名进行比较，如果这两个名称相同，则将值注入到pojo类中对应的属性上
     */

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        // 调用业务对象执行注册
        userService.reg(user);
        // 返回
        return new JsonResult<Void>(OK);
    }

    /*
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        // 创建返回值
        JsonResult<Void> result = new JsonResult<Void>();
        try {
            // 调用业务对象执行注册
            userService.reg(user);
            // 响应成功
            result.setState(200);
        } catch (UsernameDuplicateException e) {
            // 用户名被占用
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        } catch (InsertException e) {
            // 插入数据异常
            result.setState(5000);
            result.setMessage("注册失败，请联系系统管理员");
        }
        return result;
    }
    */

    /**
     *2.接收数据方式：请求处理方法的参数列表设置成飞pojo类型
     * SpringBoot会直接将请求的参数名和方法的参数名直接进行比较，如果两个名称相同，则自动完成值的依赖注入
     */

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        // 调用业务对象的方法执行登录，并获取返回值
        User data = userService.login(username, password);
        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        // System.out.println("Session中的uid=" + getUidFromSession(session));
        // System.out.println("Session中的username=" + getUsernameFromSession(session));

        // 将以上返回值和状态码OK封装到响应结果中并返回
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        // 调用session.getAttribute("")获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        // 调用业务对象执行修改密码
        userService.changePassword(uid, username, oldPassword, newPassword);
        // 返回成功
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // user对象中有四部分数据：username、phone、email、gender
        // uid数据需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    /** 设置上传文件的最大值 */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    /** 设置上传文件的类型 */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }


    /**
     * MultipartFile接口是由SpringMVC提供的接口，包装了获取文件类型的数据(任何类型的file都可以接收)
     * SpringBoot整合了SpringMVC，只需在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，
     * 然后SpringBoot会自动将传递给服务的文件数据赋值给这个参数。
     *
     * @RequestParam 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上，如果名称不一致，
     * 可以使用@RequestParam注解进行标记和映射
     *
     * @param session session对象
     * @param file 文件数据
     * @return JsonResult
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file) {
        // 判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        }
        // 判断文件类型是否为规定的后缀类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        // 上传的文件.../upload/file.png
        String parent = session.getServletContext().getRealPath("upload");
        // File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()) { // 检测目录是否存在
            dir.mkdirs();    // 创建当前的目录
        }
        // 获取到文件的名称，UUID工具来生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename=" + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename); // 此时filename是一个空文件
        // 将参数file中数据写入到这个空文件中
        try {
            file.transferTo(dest); // 将file文件中的数据写入到dest文件中(要求后缀一致)
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 返回头像的相对路径：/upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid, avatar, username);
        // 返回头像的路径给前端页面，将来用于头像的展示使用
        return new JsonResult<>(OK, avatar);
    }
}