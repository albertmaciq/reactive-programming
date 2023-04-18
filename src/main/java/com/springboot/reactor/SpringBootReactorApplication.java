package com.springboot.reactor;

import com.springboot.reactor.model.User;
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
        Flux<User> names = Flux.just("Marcos", "Irene", "Tobias", "Sandra")
            .map(n -> new User(n.toUpperCase(), null))
            .doOnNext(elem -> {
                if (elem == null) {
                   throw new RuntimeException("User can not be null");
                }
                System.out.println(elem.getName());
            }).map(n -> {
                String newFormatName = n.getName().toLowerCase();
                n.setName(newFormatName);
                return n;
            });

        names.subscribe(n -> log.info(n.toString()), error -> log.error(error.getMessage()),
            new Runnable() {
            @Override
            public void run() {
                log.info("The execution of observable has been successfully completed");
            }
        });
    }
}
