package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "for_admins/admin";
    }

    @GetMapping("/person")
    public String showUsers(@RequestParam(value = "id", defaultValue = "0") Long id, ModelMap model) {
        model.addAttribute("user", userService.getById(id));
        return "for_admins/user";

    }

    @GetMapping("/new")
    public String newUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "for_admins/new";
    }

    @PostMapping("save")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(required = false) boolean isAdmin) {
        userService.createUser(user, isAdmin);
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
        userService.updateUser(user, isAdmin);
        return "redirect:/admin";
    }

    @PostMapping("delete")
    public String delete(@RequestParam(value = "id", defaultValue = "0") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
