package com.beat.back.controler;

import com.beat.back.pojo.ResponseData;
import com.beat.back.pojo.User;
import com.beat.back.service.UserService;
import com.beat.back.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/create")
    public ResponseData<User> createUser(@RequestBody User user) {
       return  userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseData<User> loginUser(@RequestBody User user ) {
        return  userService.loginUser(user);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> geytUserById(@PathVariable S id) {
//        User user = userService.getUserById(id);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    // Implement other CRUD operations as needed
}
