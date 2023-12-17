package com.example.nt118project.util;

public class RegisterUserBody {
    private String id;
    private String name;
    private String description;
    private boolean composite;
    private boolean assigned;

    public RegisterUserBody(String id, String name, String description, boolean composite, boolean assigned) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.composite = composite;
        this.assigned = assigned;
    }
}
