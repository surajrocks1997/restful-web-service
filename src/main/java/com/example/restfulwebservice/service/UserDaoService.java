package com.example.restfulwebservice.service;

import com.example.restfulwebservice.pojo.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static Integer usersCount = 0;

    static {
        users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(24)));
        users.add(new User(++usersCount, "Michelle", LocalDate.now().minusYears(26)));
        users.add(new User(++usersCount, "Jay", LocalDate.now().minusYears(30)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findUserById(int userId) {
        return users.stream().filter((user) -> user.getId()
                .equals(userId)).findFirst().orElse(null);
    }

    public User saveUser(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public void deleteUserById(int userId) {
        users.removeIf((user) -> user.getId().equals(userId));
    }

}
