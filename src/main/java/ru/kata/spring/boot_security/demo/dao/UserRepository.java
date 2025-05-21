package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserRepository {
    List<User> getUsers();
    User getUser(int id);
    void save(User user);
    void update(int id, User user);
    void delete(int id);
}
