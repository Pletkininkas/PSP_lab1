package com.evaldasg.lab3.service;

import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.error.UserNotFoundException;
import com.evaldasg.lab3.repository.UserRepository;
import com.evaldasg.lab3.validator.UserValidator;
import com.evaldasg.lab3.validator.constant.UserField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    UserValidator userValidator = new UserValidator();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User add(User user) {
        userValidator.validateUser(user);

        return userRepository.save(user);
    }

    public void update(User user) {
        List<UserField> updateFields = new ArrayList<>();
        if (user.getEmail() != null) updateFields.add(UserField.EMAIL);
        if (user.getPassword() != null) updateFields.add(UserField.PASSWORD);
        if (user.getPhoneNumber() != null) updateFields.add(UserField.PHONE_NUMBER);
        userValidator.validateUser(user, updateFields);

        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}