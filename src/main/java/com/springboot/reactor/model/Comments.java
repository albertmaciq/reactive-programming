package com.springboot.reactor.model;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class Comments {

    private List<String> comments;


    public Comments() {
        this.comments = new ArrayList<>();
    }

    public void addComment(final String comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return "comments=" + comments;
    }
}
