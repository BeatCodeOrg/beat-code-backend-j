package com.beat.back.service;

import com.beat.back.pojo.ResponseData;
import com.beat.back.pojo.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseData createUser(User user) ;

    public  ResponseData loginUser(User user);

//    public User loginUser(String username, String password) ;
//
//    public List<User> getAllUsers();
//
//    // user database
//    public User findUserByUsername(String username);
//
//    // can be JDBC search
//    public User getUserById(int id);
//
//    // can be JDBC search
//    public User authenticateUser(String username, String password) ;
//
//    public void updateUserScore(String username, String roomCode, int score) ;

}
