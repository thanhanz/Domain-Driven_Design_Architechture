package org.thanhan.ddd.controller.resource;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.thanhan.ddd.application.service.event.EventAppService;

import java.security.SecureRandom;

@RestController
@RequestMapping("/hello")
public class HiController {

    @Autowired
    private EventAppService eventAppService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hi")
    @RateLimiter(name = "backendA", fallbackMethod = "fallbackHello")
    public String hello() {
        return eventAppService.sayHi("Jenny");
    }

    public String fallbackHello(Throwable throwable) { //Neu trong vong 2s co > 10 request => fallbackHello()
        return "Too many requests";
    }



    @GetMapping("/xinChao")
    @RateLimiter(name = "backendB", fallbackMethod = "fallbackHello")
    public String xinChao() {
        return eventAppService.sayHi("Thanh An");
    }


    private static final SecureRandom secureRandom = new SecureRandom();
    @GetMapping("/circuit/breaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackCircuitBreaker")
    public String circuitBreaker() {
        int prodId = secureRandom.nextInt(20) + 1;
        String url = "https://fakestoreapi.com/products/" + prodId;

        return restTemplate.getForObject(url, String.class);
    }

    public String fallbackCircuitBreaker(Throwable throwable) {
        return "Service fakestoreapi circuit breaker Error!!";
    }
}
