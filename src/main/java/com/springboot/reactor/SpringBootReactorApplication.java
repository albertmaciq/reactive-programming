package com.springboot.reactor;

import com.springboot.reactor.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        //exampleIterable();
        exampleFlatMap();
    }
    public void exampleFlatMap() {

        List<String> usersList = new ArrayList<>();
        usersList.add("Marcos Stomp");
        usersList.add("Irene Contreras");
        usersList.add("Tobias Harrison");
        usersList.add("Sandra Hill");
        usersList.add("Bruce Lee");
        usersList.add("Bruce Willis");

        Flux.fromIterable(usersList)
            .map(n -> new User(n.split(" ")[0].toUpperCase(), n.split(" ")[1].toUpperCase()))
            .flatMap(user -> {
                if (user.getName().equalsIgnoreCase("bruce")) {
                   return Mono.just(user);
                } else {
                    return Mono.empty();
                }
            })
            .map(u -> {
                    String newFormatName = u.getName().toLowerCase();
                    u.setName(newFormatName);
                    return u;
            })
            .subscribe(u -> log.info(u.toString()));
    }

    public void exampleIterable() {

        List<String> usersList = new ArrayList<>();
        usersList.add("Marcos Stomp");
        usersList.add("Irene Contreras");
        usersList.add("Tobias Harrison");
        usersList.add("Sandra Hill");
        usersList.add("Bruce Lee");
        usersList.add("Bruce Willis");

        Flux<String> names = Flux.fromIterable(usersList);

        Flux<User> users = names.map(n -> new User(n.split(" ")[0].toUpperCase(), n.split(" ")[1].toUpperCase()))
            .filter(user -> user.getName().equalsIgnoreCase("bruce"))
            .doOnNext(elem -> {
                if (elem == null) {
                    throw new RuntimeException("User can not be null");
                }
                System.out.println(elem.getName().concat(" ").concat(elem.getSurname()));
            }).map(u-> {
                String newFormatName = u.getName().toLowerCase();
                u.setName(newFormatName);
                return u;
            });

        users.subscribe(u -> log.info(u.toString()), error -> log.error(error.getMessage()),
            () -> log.info("The execution of observable has been successfully completed"));
    }
}
