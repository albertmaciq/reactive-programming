package com.springboot.reactor.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class UserComments {

    private User user;
    private Comments comments;
}
