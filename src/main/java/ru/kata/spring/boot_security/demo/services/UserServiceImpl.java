package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
private UserRepository userRepository;
private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository) {
        this.userRepository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    @EntityGraph(attributePaths = {"roles"})
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user, Boolean isAdmin) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        if (isAdmin) {
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        User irrelevantUser = userRepository.getById(user.getId());
        if (BCrypt.checkpw(user.getPassword(), irrelevantUser.getPassword())) {
            user.setPassword(irrelevantUser.getPassword());
        } else {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        }
        userRepository.save(user);
    }

    @Override
    public void createUser(User user, Boolean isAdmin) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        if (isAdmin) {
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }
}
