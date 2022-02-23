package com.at.webfluxdemo1.controller;

import com.at.webfluxdemo1.entity.User;
import com.at.webfluxdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // 根据id查询用户
    @GetMapping("/user/{id}")
    public Mono<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // 查询所有用户
    @GetMapping("/user")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 添加用户
    @PostMapping("/saveuser")
    public Mono<Void> saveUser(@RequestBody User user) {
        Mono<User> userMono = Mono.just(user);
        return userService.savaUserInfo(userMono);
    }
}
