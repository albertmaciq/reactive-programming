package com.springboot.reactor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;


@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        Flux<String> names = Flux.just("Marcos", "Irene", "Tobias", "Sandra")
            .doOnNext(System.out::println);

        names.subscribe();
    }
}
