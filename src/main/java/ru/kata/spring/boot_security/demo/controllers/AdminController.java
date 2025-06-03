package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public AdminController(UserRepository carService,
                           RoleRepository roleRepository) {
        this.userRepository = carService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String showUsers(@RequestParam(value = "id", defaultValue = "0") Long id, ModelMap model) {
        if (id != 0) {
            model.addAttribute("user", userRepository.getById(id));
            return "for_admins/user";
        }
        model.addAttribute("users", userRepository.findAll());
        return "for_admins/admin";
    }

    @GetMapping("/new")
    public String newUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "for_admins/new";
    }

    @PostMapping("save")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(required = false) boolean isAdmin) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        if (isAdmin) {
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return "redirect:/admin";
    }

    @PostMapping("edit")
    public String edit(@RequestParam(value = "id", defaultValue = "0") Long id, ModelMap model) {
        model.addAttribute("user", userRepository.getById(id));
        return "for_admins/edit";
    }

    @PostMapping("update")
    public String update(User user) {
        userRepository.save(user);
        return "redirect:/admin";
    }

    @PostMapping("delete")
    public String delete(@RequestParam(value = "id", defaultValue = "0") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }
}
