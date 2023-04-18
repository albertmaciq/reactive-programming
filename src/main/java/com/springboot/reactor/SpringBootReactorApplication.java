package com.springboot.reactor;

import com.springboot.reactor.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        List<String> usersList = new ArrayList<>();
        usersList.add("Marcos Stomp");
        usersList.add("Irene Contreras");
        usersList.add("Tobias Harrison");
        usersList.add("Sandra Hill");
        usersList.add("Bruce Lee");
        usersList.add("Bruce Willis");

        Flux<String> names = Flux.fromIterable(usersList);

        Flux<User> users = names.map(n -> new User(n.split(" ")[0].toUpperCase(), n.split(" ")[1].toUpperCase()))
            .filter(user -> user.getName().toLowerCase().equals("bruce"))
            .doOnNext(elem -> {
                if (elem == null) {
                   throw new RuntimeException("User can not be null");
                }
                System.out.println(elem.getName().concat(" ").concat(elem.getSurname()));
            }).map(n -> {
                String newFormatName = n.getName().toLowerCase();
                n.setName(newFormatName);
                return n;
            });

        users.subscribe(n -> log.info(n.toString()), error -> log.error(error.getMessage()),
            () -> log.info("The execution of observable has been successfully completed"));
    }
}
