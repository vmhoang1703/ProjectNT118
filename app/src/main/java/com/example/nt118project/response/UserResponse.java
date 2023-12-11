package com.example.nt118project.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    private String id;
    private String realm;
    private String realmId;
    @SerializedName("email")
    public String email;
    private long createdOn;
    private boolean serviceAccount;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("username")
    public String username;
    public String getId() {
        return id;
    }

    public String getRealm() {
        return realm;
    }

    public String getRealmId() {
        return realmId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
}
