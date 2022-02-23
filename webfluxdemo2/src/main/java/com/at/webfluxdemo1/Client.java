package com.at.webfluxdemo1;

import com.at.webfluxdemo1.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Client {
    public static void main(String[] args) {
        WebClient client = WebClient.create("http://127.0.0.1:52120");
        int id = 1;
        User user = client.get().uri("/users/{id}", id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(User.class).block();
        System.out.println(user.getName());

        Flux<User> results = client.get().uri("/users").accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(User.class);
        results.map(u -> u.getName()).buffer().doOnNext(System.out::println).blockFirst();
    }
}
