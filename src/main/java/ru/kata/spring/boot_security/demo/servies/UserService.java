package ru.kata.spring.boot_security.demo.servies;


import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUser(long id);
    void save(User user);
    void update(long id, User user);
    void delete(long id);
}
