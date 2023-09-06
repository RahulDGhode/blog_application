package com.blog.blog.Exceptions;

public class BlogAPIException extends RuntimeException {
    public BlogAPIException(String massage) {
        super(massage);
    }
}
