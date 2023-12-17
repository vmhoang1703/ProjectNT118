package com.example.nt118project.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("realm")
    private String realm;
    @SerializedName("realmId")
    private String realmId;
    @SerializedName("email")
    public String email;
    @SerializedName("createdOn")
    private long createdOn;
    @SerializedName("serviceAccount")
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
    public long getCreatedOn() {
        return createdOn;
    }
}
