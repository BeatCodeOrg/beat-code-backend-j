package com.beat.back.pojo;

import lombok.Data;

@Data
public class User {

    public int user_id; // id in the database.
    public String username;
    public String pw;
    public int score;

    private  String passwordVerify;

    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + pw + '\'' +
                '}';
    }

    public String getPassword() {
        return pw;
    }
    public String getUsername() {
        return username;
    }


}
