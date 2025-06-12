package ru.kata.spring.boot_security.demo.services;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface UserService {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);
    User getById(Long id);
    List<User> findAll();
    void save(User user);
    void deleteById(Long id);
}
