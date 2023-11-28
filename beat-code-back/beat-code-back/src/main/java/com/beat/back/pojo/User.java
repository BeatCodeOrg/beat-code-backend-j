package com.beat.back.pojo;

import lombok.Data;

@Data
public class User {

    public String id; // id in the database.
    public String username;
    public String password;
    public int score;

    private  String passwordVerify;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }




}
