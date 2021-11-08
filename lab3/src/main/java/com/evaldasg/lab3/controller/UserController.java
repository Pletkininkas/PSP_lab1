package com.evaldasg.lab3.controller;

import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> add(@RequestBody User user) {
        val addedUser = userService.add(user);

        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<User> update(@RequestBody User user) {
        if (user.getId() == null)
            return ResponseEntity.badRequest().build();

        userService.update(user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}",  produces = {"application/json"})
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (userService.findById(id) != null)
            userService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
