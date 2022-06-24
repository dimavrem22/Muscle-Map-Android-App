package com.example.cs4520project;

import java.io.Serializable;

public class Profile implements Serializable {
    private final String email;
    private final String name;

    public Profile(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
