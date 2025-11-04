package com.family.server.model;

import java.sql.Timestamp;
import java.util.UUID;

public class User {
    private UUID id;
    private String email, password;
    private Timestamp createAt;

    public User() {}
    public User(UUID id, String email, String password, Timestamp createAt)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
    }
    public UUID getId() {return id;}
    public String getEmail() {return email;}
    public  String getPassword() {return  password;}
    public Timestamp getCreateAt() {return createAt;}

    public void setId(UUID id) {this.id = id;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public  void setCreateAt(Timestamp createAt) {this.createAt = createAt;}
}
