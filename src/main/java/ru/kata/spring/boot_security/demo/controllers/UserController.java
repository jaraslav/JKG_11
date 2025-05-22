package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.servies.UserService;


@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService carService) {
        this.userService = carService;
    }

    @GetMapping()
    public String showUsers(@RequestParam(value = "id", defaultValue = "0") int id, ModelMap model) {
        if (id != 0) {
            model.addAttribute("user", userService.getUser(id));
            return "pages/user";
        }
        model.addAttribute("users", userService.getUsers());
        return "pages/users";
    }

    @GetMapping("/new")
    public String newUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "pages/new";
    }

    @PostMapping("save")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", defaultValue = "0") int id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        return "pages/edit";
    }

    @PostMapping("update")
    public String update(@RequestParam(value = "id", defaultValue = "0") int id, User user) {
        userService.update(id, user);
        return "redirect:/users";
    }

    @PostMapping("delete")
    public String delete(@RequestParam(value = "id", defaultValue = "0") int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
