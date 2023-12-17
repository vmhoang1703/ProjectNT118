package com.example.nt118project.response;

import com.google.gson.annotations.SerializedName;

public class UserRoles {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("composite")
    public String composite;
    @SerializedName("assigned")
    public String assigned;

    public UserRoles(String id, String name, String description, String composite, String assigned) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.composite = composite;
        this.assigned = assigned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public String getComposite() {
        return composite;
    }


    public String getAssigned() {
        return assigned;
    }


    @Override
    public String toString() {
        return "UserRoles{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", composite='" + composite + '\'' +
                ", assigned='" + assigned + '\'' +
                '}';
    }
}
