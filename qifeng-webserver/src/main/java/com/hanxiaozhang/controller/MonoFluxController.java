package com.hanxiaozhang.controller;

import com.hanxiaozhang.util.ClientUser;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("flux")
public class MonoFluxController {

    public static void main(String[] args) throws InterruptedException, IllegalAccessException {
        ClientUser user = new ClientUser("jeetess.com","number add ninenine");
        ClassLoader classLoader = user.getClass().getClassLoader();
        System.out.println(classLoader);
        //启动类加载器，是Java类加载层次中最顶层的类加载器
        URL [] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url:urls
             ) {
            System.out.println(url.toExternalForm());
        }
        Launcher launcher = sun.misc.Launcher.getLauncher();
        System.out.println(launcher.getClassLoader());
        System.out.println(String.class.getClassLoader());
        System.out.println(Throwable.class.getClassLoader());
        Field [] fields = user.getClass().getDeclaredFields();
        for (Field field:
             fields) {
            field.setAccessible(true);
            System.out.println("name:"+field.getName());
            System.out.println("un:"+field.get(user));
        }
    }

    @RequestMapping("/hello")
    public Optional<String> hello(@RequestParam(required = false) String str){
        if(StringUtils.isEmpty(str)){
            return Optional.empty();
        }
        return Optional.of("业余草：www.xttblog.com");
    }

    @RequestMapping("/mono")
    public Mono<String> mono(@RequestParam(required = false) String str){
        if(StringUtils.isEmpty(str)){
            return Mono.empty();
        }
        return Mono.just("业余草：www.xttblog.com");
    }

    public static void createFlux() throws InterruptedException {
        //整型
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5);
        //字符串
        Flux<String> stringFlux = Flux.just("hello", "world");
        List<String> list = Arrays.asList("hello", "world");
        //列表
        Flux<String> stringFlux1 = Flux.fromIterable(list);
        //范围
        Flux<Integer> integerFlux1 = Flux.range(1, 5);
        //时间间隔
        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(1000));
        longFlux.subscribe(System.out::println);

        //Flux 创建
        Flux<String> stringFlux2 = Flux.from(stringFlux1);
        stringFlux2.subscribe(System.out::println);
        //TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

}
