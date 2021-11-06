package com.evaldasg.lab3.repository;

import com.evaldasg.lab3.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Sql(scripts = {"classpath:/db/users-cleanup.sql"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        User user = new User("Name", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        userRepository.save(user);
        User newUser = userRepository.findById(user.getId()).get();

        assertNotNull(newUser);
        assertNotNull(newUser.getId());
        assertEquals(user.getName(), newUser.getName());
        assertEquals(user.getPhoneNumber(), newUser.getPhoneNumber());
    }

    @Test
    public void testUpdate() {
        User user = new User("Name", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        userRepository.save(user);
        User insertedUser = userRepository.findById(user.getId()).get();

        insertedUser.setEmail("123asd@gmail.com");
        insertedUser.setAddress("New location name");

        userRepository.save(insertedUser);

        User updatedUser = userRepository.findById(user.getId()).get();

        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getName(), updatedUser.getName());
        assertEquals(insertedUser.getSurname(), updatedUser.getSurname());
        assertEquals(insertedUser.getPhoneNumber(), updatedUser.getPhoneNumber());
        assertEquals(insertedUser.getEmail(), updatedUser.getEmail());
        assertEquals(insertedUser.getAddress(), updatedUser.getAddress());
        assertEquals(insertedUser.getPassword(), updatedUser.getPassword());
    }

    @Test
    public void testFindAll() {
        User user1 = new User("Test1", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        User user2 = new User("Test2", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    @Test
    public void testFindById() {
        User user1 = new User("Test1", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        User user2 = new User("Test2", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        userRepository.save(user1);
        userRepository.save(user2);

        User foundUser = userRepository.findById(user2.getId()).get();

        assertEquals(user2, foundUser);
    }

    @Test
    public void testDeleteById() {
        User user = new User("Test1", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        userRepository.save(user);

        userRepository.deleteById(user.getId());

        assertEquals(0, userRepository.findAll().size());
    }
}
