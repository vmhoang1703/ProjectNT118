package com.example.nt118project;

public class User {
    private String username;
    private String email;

    public User() {
        // Required default constructor for Firebase
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

