package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
     private UserRepository repository;

     @Autowired
     public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
     }

     @Override
     @EntityGraph(attributePaths = {"roles"})
     public Optional<User> findByEmail(String email) {
         return repository.findByEmail(email);
     }

    @Override
     public User getById(Long id) {
         return repository.getById(id);
     }

    @Override
     public List<User> findAll() {
         return repository.findAll();
     }

    @Override
     public void save(User user) {
         repository.save(user);
     }
     @Override
     public void deleteById(Long id) {
         repository.deleteById(id);
     }


}
