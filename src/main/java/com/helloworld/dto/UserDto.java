package com.helloworld.dto;

/**
 * Created by kasper on 2/14/14.
 */
public class UserDto {
    private String name;
    private String email;

    public UserDto () {}

    public UserDto(String name, String email) {
        setName(name);
        setEmail(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
