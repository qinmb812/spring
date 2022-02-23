package com.at.webfluxdemo1.handle;

import com.at.webfluxdemo1.entity.User;
import com.at.webfluxdemo1.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class UserHandle {
    private final UserService userService;

    public UserHandle(UserService userService) {
        this.userService = userService;
    }

    // 根据id查询用户
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        // 获取id值
        int userId = Integer.parseInt(request.pathVariable("id"));
        // 空值处理
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        // 调用Service方法得到数据
        Mono<User> userMono = this.userService.getUserById(userId);
        // 把userMono进行转换返回
        // 使用Reactor操作符flatMap
        return userMono
                .flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(person)))
                .switchIfEmpty(notFound);
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userMono, User.class);
    }

    // 查询所有用户
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        // 调用Service得到结果
        Flux<User> users = this.userService.getAllUsers();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users, User.class);
    }

    // 添加用户
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        // 得到User对象
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(this.userService.savaUserInfo(userMono));
    }
}
