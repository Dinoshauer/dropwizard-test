package com.helloworld.core;

/**
 * Created by kasper on 2/14/14.
 */
public class Saying {
    private final long id;
    private final String content;

    public Saying (long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
