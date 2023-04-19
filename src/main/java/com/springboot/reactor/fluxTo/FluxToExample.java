package com.springboot.reactor.fluxTo;

import com.springboot.reactor.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class FluxToExample {

    public static final Logger LOG = LoggerFactory.getLogger(FluxToExample.class);

    public void observableToMonoExample() {

        LOG.info("observable To Mono Example:".toUpperCase());

        List<User> usersList = new ArrayList<>();
        usersList.add(new User("Marcos", "Stomp"));
        usersList.add(new User("Irene", "Contreras"));
        usersList.add(new User("Tobias", "Harrison"));
        usersList.add(new User("Sandra", "Hill"));
        usersList.add(new User("Bruce", "Lee"));
        usersList.add(new User("Bruce", "Willis"));

        Flux.fromIterable(usersList)
            .collectList()
            .subscribe(user -> user.forEach(elem -> LOG.info(elem.toString())));
    }

    public void toStringExample() {

        LOG.info("toString Example:".toUpperCase());

        List<User> usersList = new ArrayList<>();
        usersList.add(new User("Marcos", "Stomp"));
        usersList.add(new User("Irene", "Contreras"));
        usersList.add(new User("Tobias", "Harrison"));
        usersList.add(new User("Sandra", "Hill"));
        usersList.add(new User("Bruce", "Lee"));
        usersList.add(new User("Bruce", "Willis"));

        Flux.fromIterable(usersList)
            .map(user -> user.getName().toUpperCase().concat(" ")
                .concat(user.getSurname().toUpperCase()))
            .flatMap(name -> {
                if (name.contains("BRUCE")) {
                    return Mono.just(name);
                } else {
                    return Mono.empty();
                }
            })
            .map(String::toLowerCase)
            .subscribe(LOG::info);
    }

    public void iterableExample() {

        LOG.info("iterable Example:".toUpperCase());

        List<String> usersList = new ArrayList<>();
        usersList.add("Marcos Stomp");
        usersList.add("Irene Contreras");
        usersList.add("Tobias Harrison");
        usersList.add("Sandra Hill");
        usersList.add("Bruce Lee");
        usersList.add("Bruce Willis");

        Flux<String> names = Flux.fromIterable(usersList);

        Flux<User> users = names.map(n -> new User(n.split(" ")[0].toUpperCase(),
                n.split(" ")[1].toUpperCase()))
            .filter(user -> user.getName().equalsIgnoreCase("bruce"))
            .doOnNext(elem -> {
                if (elem == null) {
                    throw new RuntimeException("User can not be null");
                }
                System.out.println(elem.getName().concat(" ").concat(elem.getSurname()));
            }).map(u -> {
                String newFormatName = u.getName().toLowerCase();
                u.setName(newFormatName);
                return u;
            });

        users.subscribe(u -> LOG.info(u.toString()), error -> LOG.error(error.getMessage()),
            () -> LOG.info("The execution of observable has been successfully completed"));
    }
}
