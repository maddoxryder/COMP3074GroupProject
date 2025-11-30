package com.example.clubmanager.data.models;

public class Staff {
    private final String id;
    private final String name;
    private final String role;

    public Staff(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
}
