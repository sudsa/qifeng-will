package com.qifeng.will.util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LamTet {

    public static int totalSelectedValues(List<Integer> values,
                                          Predicate<Integer> selector) {
        return values.stream()
                .filter(selector)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>();
        values.add(12);
        values.add(19);
        values.add(1);
        int re = totalSelectedValues(values,integer -> integer%2==0);
        System.out.println(re);
        System.out.println(totalSelectedValues(values, NumUtil::isEven));

        NumUtil.createIsOdd().test(10);

        currentUser_mono().flatMap(clientUser ->{
            return Mono.empty();
        });
    }

    //传统数据处理
    public List<ClientUser> allUsers() {
        return Arrays.asList(new ClientUser("felord.cn", "reactive"),
                new ClientUser("Felordcn", "Reactor"));
    }

    //流式数据处理
    public static Stream<ClientUser> allUsersStream() {
        return  Stream.of(new ClientUser("felord.cn", "reactive"),
                new ClientUser("Felordcn", "Reactor"));
    }

    //反应式数据处理
    //Flux 是一个发出(emit)0-N个元素组成的异步序列的Publisher<T>,
    // 可以被onComplete信号或者onError信号所终止。
    // 在响应流规范中存在三种给下游消费者调用的方法 onNext, onComplete, 和onError。
    public static Flux<ClientUser> allUsersFlux(){
        return Flux.just(new ClientUser("felord.cn", "reactive"),
                new ClientUser("Felordcn", "Reactor"));
    }

    static boolean isAuthenticated = true;

    //传统数据处理
    public static ClientUser currentUser() {
        return isAuthenticated ? new ClientUser("felord.cn", "reactive") : null;
    }

    //Optional的处理方式
    public static Optional<ClientUser> currentUser_optional () {
        return isAuthenticated ? Optional.of(new ClientUser("felord.cn", "reactive"))
                : Optional.empty();
    }

    //反应式数据处理
    //MONOMono 是一个发出(emit)0-1个元素的Publisher<T>,可以被onComplete信号或者onError信号所终止。
    public static Mono<ClientUser> currentUser_mono () {
        return isAuthenticated ? Mono.just(new ClientUser("felord.cn", "reactive"))
                : Mono.empty();
    }


}
