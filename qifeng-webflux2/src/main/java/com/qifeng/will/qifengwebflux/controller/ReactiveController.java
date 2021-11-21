package com.qifeng.will.qifengwebflux.controller;

import com.qifeng.will.qifengwebflux.dto.Quote;
import com.qifeng.will.qifengwebflux.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("react")
public class ReactiveController {

    @RequestMapping("test1")
    public void clientTest(){
        WebClient webClient = WebClient.create("http://localhost:8080");   // 1
        Mono<String> resp = webClient
                .get().uri("/flux/hello") // 2
                .retrieve() // 3
                .bodyToMono(String.class);  // 4
            resp.subscribe(System.out::println);    // 5
        try {
            TimeUnit.SECONDS.sleep(1);  // 6
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("test2")
    public Flux<User> clientTest2() throws InterruptedException {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build(); // 1
        return webClient
                .get().uri("/flux/user")
                .accept(MediaType.APPLICATION_STREAM_JSON) // 2
                .exchange() // 3
                .flatMapMany(response -> response.bodyToFlux(User.class))   // 4
                .doOnNext(System.out::println)  // 5
                //.blockLast();   // 6
                ;
    }


    @RequestMapping("test3")
    public void fetchQuotes() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build(); // 1
            webClient
                // We then create a GET request to test an endpoint
                .get().uri("/quotes?size=20")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                //.expectStatus().isOk()
                //.expectHeader().contentType(MediaType.APPLICATION_JSON)
                 .flatMapMany(response->response.toEntityList(Queue.class))
                    .doOnNext(System.out::print);
                //.expectBodyList(Quote.class)
                //.hasSize(20)
                // Here we check that all Quotes have a positive price value
                //.consumeWith(allQuotes ->
                 //       assertThat(allQuotes.getResponseBody())
                 //               .allSatisfy(quote -> assertThat(quote.getPrice()).isPositive()));
    }

    @RequestMapping("test4")
    public void fetchQuotesAsStream() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build(); // 1
            webClient
                // We then create a GET request to test an endpoint
                .get().uri("/quotes")
                // this time, accepting "application/stream+json"
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Quote.class)
                .share()
                .log("io.spring.workshop.tradingservice");
                //.exchange()
                //.flatMapMany(clientResponse -> clientResponse.toEntityList(Quote.class))
                //.block();
                // and use the dedicated DSL to test assertions against the response
               //.expectStatus()
                //.isOk()
               // .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON)
                //.returnResult(Quote.class)
               // .getResponseBody()
                //.take(30)
                //.collectList()
                //.block();
    }


}
