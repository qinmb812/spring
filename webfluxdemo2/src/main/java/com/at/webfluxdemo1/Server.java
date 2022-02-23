package com.at.webfluxdemo1;

import com.at.webfluxdemo1.handle.UserHandle;
import com.at.webfluxdemo1.service.UserService;
import com.at.webfluxdemo1.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;


public class Server {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.createReatorServer();
        System.out.println("enter to exit");
        System.in.read();
    }

    // 1 创建Router路由
    public RouterFunction<ServerResponse> routingFunction() {
        // 创建handle对象
        UserService userService = new UserServiceImpl();
        UserHandle handle = new UserHandle(userService);
        // 设置路由
        return RouterFunctions.route(
                RequestPredicates.GET("/users/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handle::getUserById)
                .andRoute(RequestPredicates.GET("/users").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handle::getAllUsers)
                .andRoute(RequestPredicates.POST("/saveuser").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handle::saveUser);
    }

    // 2 创建服务器完成适配
    public void createReatorServer() {
        // 路由和handle适配
        RouterFunction<ServerResponse> route = routingFunction();
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(adapter).bindNow();
    }
}
