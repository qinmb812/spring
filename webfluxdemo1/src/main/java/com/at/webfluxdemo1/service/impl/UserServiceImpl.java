package com.at.webfluxdemo1.service.impl;

import com.at.webfluxdemo1.entity.User;
import com.at.webfluxdemo1.service.UserService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserServiceImpl implements UserService {
    // 创建map集合存储数据
    private final Map<Integer, User> users = new HashMap<>();

    public UserServiceImpl() {
        this.users.put(1, new User("Lucy", "女", 20));
        this.users.put(2, new User("Mary", "女", 25));
        this.users.put(3, new User("Jack", "男", 33));
    }

    // 根据id查询
    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    // 查询多个用户
    @Override
    public Flux<User> getAllUsers() {
        return Flux.fromIterable(this.users.values());
    }

    //添加用户
    @Override
    public Mono<Void> savaUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(person -> {
            // 向map集合里面放值
            Integer id = this.users.size() + 1;
            this.users.put(id, person);
        }).thenEmpty(Mono.empty());
    }
}
