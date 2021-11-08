package com.evaldasg.lab3.controller;

import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        val addedUser = userService.add(user);

        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (userService.findById(user.getId()) == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        if (userService.findById(id) != null)
            userService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
