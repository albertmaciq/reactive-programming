package com.springboot.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;


@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        Flux<String> names = Flux.just("Marcos", "", "Irene", "Tobias", "Sandra")
            .doOnNext(elem -> {
                if (elem.isEmpty()) {
                   throw new RuntimeException("Names can not be empty");
                }
                System.out.println(elem);
            });

        names.subscribe(log::info, error -> log.error(error.getMessage()));
    }
}
