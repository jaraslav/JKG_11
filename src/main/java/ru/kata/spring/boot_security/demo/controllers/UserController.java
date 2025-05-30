package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.servies.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService carService) {
        this.userService = carService;
    }

    @GetMapping()
    public String showUsers(@RequestParam(value = "id", defaultValue = "0") int id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        return "pages/for_user";
    }
}
