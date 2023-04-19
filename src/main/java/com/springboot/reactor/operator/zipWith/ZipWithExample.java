package com.springboot.reactor.operator.zipWith;

import com.springboot.reactor.model.Comments;
import com.springboot.reactor.model.User;
import com.springboot.reactor.model.UserComments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ZipWithExample {

    public static final Logger LOG = LoggerFactory.getLogger(ZipWithExample.class);

    public void userCommentsZipWithExample() {

        LOG.info("userComments ZipWith Example:".toUpperCase());

        Mono<User> userMono = Mono.fromCallable(() -> new User("John", "Doe"));

        Mono<Comments> commentsMono = Mono.fromCallable(() -> {
            Comments comments = new Comments();
            comments.addComment("Hello");
            comments.addComment("I am freaking out");
            comments.addComment("that was awesome");
            return comments;
        });

        Mono<UserComments> userCommentsMono = userMono.zipWith(commentsMono, UserComments::new);
        userCommentsMono.subscribe(uc -> LOG.info(uc.toString()));
    }

    public void userCommentsZipWithExample2() {

        LOG.info("userComments ZipWith Example 2:".toUpperCase());

        Mono<User> userMono = Mono.fromCallable(() -> new User("John", "Doe"));

        Mono<Comments> commentsMono = Mono.fromCallable(() -> {
            Comments comments = new Comments();
            comments.addComment("Hello");
            comments.addComment("I am freaking out");
            comments.addComment("that was awesome");
            return comments;
        });

        Mono<UserComments> userCommentsMono = userMono
            .zipWith(commentsMono)
            .map(tuple -> {
                User user = tuple.getT1();
                Comments comments = tuple.getT2();
                return new UserComments(user, comments);
            });

        userCommentsMono.subscribe(uc -> LOG.info(uc.toString()));
    }
}
