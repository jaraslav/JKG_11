package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;


import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService carService,
                           RoleService roleService) {
        this.userService = carService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showUsers(@RequestParam(value = "id", defaultValue = "0") Long id, ModelMap model) {
        if (id != 0) {
            model.addAttribute("user", userService.getById(id));
            return "for_admins/user";
        }
        model.addAttribute("users", userService.findAll());
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
        roles.add(roleService.findByName("ROLE_USER"));
        if (isAdmin) {
            roles.add(roleService.findByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("edit")
    public String edit(@RequestParam(value = "id", defaultValue = "0") Long id, ModelMap model) {
        model.addAttribute("user", userService.getById(id));
        return "for_admins/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(required = false) boolean isAdmin) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName("ROLE_USER"));
        if (isAdmin) {
            roles.add(roleService.findByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("delete")
    public String delete(@RequestParam(value = "id", defaultValue = "0") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
