package com.springboot.reactor.operator.flatMap;

import com.springboot.reactor.model.Comments;
import com.springboot.reactor.model.User;
import com.springboot.reactor.model.UserComments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class FlatMapExample {

    public static final Logger LOG = LoggerFactory.getLogger(FlatMapExample.class);

    public void exampleFlatMap() {

        LOG.info("example FlatMap".toUpperCase());

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
            .subscribe(u -> LOG.info(u.toString()));
    }

    public void exampleUserCommentsFlatMap() {

        LOG.info("example User Comments FlatMap".toUpperCase());

        Mono<User> userMono = Mono.fromCallable(() -> new User("John", "Doe"));

        Mono<Comments> commentsMono = Mono.fromCallable(() -> {
            Comments comments = new Comments();
            comments.addComment("Hello");
            comments.addComment("I am freaking out");
            comments.addComment("that was awesome");
            return comments;
        });

        userMono.flatMap(u -> commentsMono.map(c -> new UserComments(u, c)))
            .subscribe(uc -> LOG.info(uc.toString()));
    }
}
