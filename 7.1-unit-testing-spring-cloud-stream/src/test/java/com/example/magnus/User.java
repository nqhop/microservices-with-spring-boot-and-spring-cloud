package com.example.magnus;

public class User {
    private final String name;
    private final boolean active;

    public User(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }
}
