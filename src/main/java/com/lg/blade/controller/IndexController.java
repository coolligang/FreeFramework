package com.lg.blade.controller;

import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.request.Form;
import com.hellokaton.blade.annotation.request.PathParam;
import com.hellokaton.blade.annotation.route.DELETE;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.annotation.route.PUT;
import com.hellokaton.blade.ioc.annotation.Inject;
import com.hellokaton.blade.kit.JsonKit;
import com.hellokaton.blade.mvc.http.HttpMethod;
import com.hellokaton.blade.mvc.http.Request;
import com.hellokaton.blade.mvc.http.Response;
import com.hellokaton.blade.mvc.multipart.FileItem;
import com.hellokaton.blade.mvc.ui.RestResponse;
import com.lg.blade.bean.UserService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


/**
 * ProtocolFreeFramework
 * IndexController
 * 在 Blade 中标识一个类是控制器类非常简单，只需要在该类上面添加一个 @Path 注解即可，
 * 为了不使该类的配置过于复杂我们允许用户在该类上不出现 @Bean 注解，其实是框架帮你做了。
 * 这样的好处是，使用 @Path 可以配置这个控制器的一些特性，比如 namespace、restful 参数等。
 * 这样这个 IndexController 就被 Blade 认为是一个控制器，在启动的时候将它托管起来，
 * 当你访问某个路由的时候会找到这个对象，得到复用。
 *
 * @author: ligang30
 * @date: 2022/6/17
 */

@Path
public class IndexController {
    // 使用自定义组件
//    @Inject
//    private UserService userService;

    @GET("/index")
    public void renderIndex(Response response) {
        response.render("index.html");
    }

    @GET("/index2")
    public String renderIndex2(Response response) {
        return "index.html";
    }

    @GET("/")
    public String index() {
        return "index.html";
    }

    // 获取表单参数
    @GET("/home")
    public String home(@Form String name) {
        System.out.println("name: " + name);
        return "index.html";
    }

    // 获取 Path 参数
    @GET("/users/:uid")
    public void users(@PathParam Integer uid) {
        System.out.println("uid: " + uid);
    }

    // 获取上传文件参数
//    @POST("/upload")
//    public void upload(@Multipart FileItem fileItem){
//        byte[] data = fileItem.getData();
//        // Save the temporary file to the specified path
//        Files.write(Paths.get(filePath), data);
//    }

    // 获取 body 参数
    @GET("/bodyParam")
    public void users(@Body Request payRequest) {
        System.out.println("payRequest: " + JsonKit.toJson(payRequest));
    }

    @POST("/save/default/:uid")
    public void formParams(Request request) {
        String username = request.bodyToString();
        Integer uid = request.pathInt("uid");
        System.out.println("uid=" + uid + "   username=" + username.toString());
    }

    @POST("/save")
    public void saveUser(@Form String username) {
        System.out.println("username:" + username);
    }

    @POST("/save/data")
    public void savePerson(@Form String username, @Form Integer age){
        System.out.println("username is:" + username + ", age is:" + age);
    }

    @POST("/body")
    public void jsonBody(@Body Map<String,Object> body,Response response){
        System.out.println("body="+body.toString());
        response.status(201);
        User user = new User("ligang", "ligang.me@gmail.com");
        response.json(user);
    }

    @PUT("/update")
    public void updateUser(@Form String username) {
        System.out.println("username:" + username);
    }

    @DELETE("/delete/:uid")
    public void deleteUser(@PathParam Integer uid) {
        System.out.println("delete user:" + uid);
    }

    // 绑定类
    @POST("/users/bond")
    public void bodyParams(User user) {
        //此时接收的表单参数或者 Request Body 的 JSON 参数会被自动映射为一个 User 对象。
    }
}
