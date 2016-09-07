package com.ljubo.application.model.users;

/**
 * Created by ljubo on 9/7/2016.
 */

import java.io.Serializable;


public class User implements Serializable {

    private final String username;
    private String password;
    private String email;
    private String address;

    public User(String username, String password, String email, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
