package com.hanxiaozhang.reactive;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;

public class FlowDemo {
    private static String array[] = {"1232","34567","34ere","8970"};
    public static void deom()
    {
        Flux.fromArray(array).subscribe().dispose();
        Flux<String> tt = Flux.fromStream(IntStream.range(1,10).mapToObj(i->{

            return "flux"+i;
        }));

        Flux.interval(Duration.ofSeconds(500)).subscribe(val->handle(val),e->handleErr(e),onComplete());
    }

    public static void handle(long num){

    }

    public static void handleErr(Throwable e){
        e.printStackTrace();
    }

    public static Runnable onComplete(){

        return null;
    }
}
