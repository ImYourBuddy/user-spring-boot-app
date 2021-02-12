package com.mycompany.controllers;

import com.mycompany.models.User;
import com.mycompany.services.DataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    private final DataHandler handler;

    @Autowired
    public UserController(DataHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/new-user")
    public String newEmployeeForm(Model model) {
        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping("/new-user")
    public String newEmployeeResult(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user";
        }
        if(!handler.saveToFile(user)) {
            return "user-already-exists";
        }
        return "user-result";
    }

    @GetMapping("/search-user")
    public String searchEmployeeForm(Model model) {
        model.addAttribute("user", new User());
        return "search-user";
    }

    @PostMapping("/search-user")
    public String searchEmployeeResult(@ModelAttribute  User user, HttpServletRequest req, Model model) {
        User foundUser = handler.searchUser(user);
        if (foundUser == null) {
            return "user-not-found";
        }
        model.addAttribute(foundUser);
        return "search-result";
    }
}
