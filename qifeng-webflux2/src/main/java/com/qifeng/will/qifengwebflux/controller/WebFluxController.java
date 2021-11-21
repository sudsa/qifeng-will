package com.qifeng.will.qifengwebflux.controller;

import com.qifeng.will.qifengwebflux.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping("flux")
@Slf4j
public class WebFluxController {

    private WebClient webClient;

    // 阻塞5秒钟
    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
        }
        return "some string";
    }

    // 普通的SpringMVC方法
    @GetMapping("/1")
    private String get1() {
        log.info("get1 start");
        String result = createStr();
        log.info("get1 end.");
        return result;
    }

    // WebFlux(返回的是Mono) vWebFlux的好处：能够以固定的线程来处理高并发
    @GetMapping("/2")
    private Mono<String> get2() {
        log.info("get2 start");
        Mono<String> result = Mono.fromSupplier(() -> createStr());
        log.info("get2 end.");
        return result;
    }

    //每秒会给浏览器推送数据
    @GetMapping(value = "/interval", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> flux() {
        Flux<String> result = Flux
                .fromStream(IntStream.range(1, 13).mapToObj(i -> {
                   log.info("i="+i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                    }
                    return "this data is " + i;
                }));
        return result;
    }

    @GetMapping("mono")
    public Mono<Object> mono() {
        return Mono.create(monoSink -> {
            log.info("创建 Mono");
            monoSink.success("hello webflux");
        })
                .doOnSubscribe(subscription -> { //当订阅者去订阅发布者的时候，该方法会调用
                    log.info("subscription={}", subscription);
                }).doOnNext(o -> { //当订阅者收到数据时，改方法会调用
                    log.info("o={}", o);
                });
    }

    @GetMapping("flux")
    public Flux<String> fluxsss() {
        return Flux.just("hello", "webflux", "spring", "boot");
    }


    @GetMapping("user")
    public Flux<User> user() {
        User user = new User("1","jack","1899976965","98976786@sina.com","南路木",new Date());
        return Flux.just(user);
    }

    @GetMapping("hello")
    public Mono<String> hello() {

        return Mono.fromSupplier(()->createStr()).just("hello");
    }

    //返回List集合
    @GetMapping("userList")
    public Flux<User> userList() {
        List<User> list = new ArrayList<>();
        IntStream.range(1,10).forEach(i->{
            User user = new User("No:1000"+i,"jack",
                    "1899976965",
                    "98976786@sina.com","南路木",new Date());
            list.add(user);
        });
        return Flux.fromIterable(list);
    }


    //返回MAP集合
    @GetMapping("userMap")
    public Flux<Map.Entry<String, User>> userMap() {
        Map<String, User> map = new HashMap<>();
        IntStream.range(1,10).forEach(i->{
            User user = new User("No:1000"+i,"jack",
                    "1899976965",
                    "98976786@sina.com","南路木",new Date());
            map.put(user.getId(),user);
        });

        return Flux.fromIterable(map.entrySet());
    }

}
