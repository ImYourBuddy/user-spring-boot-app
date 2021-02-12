package com.mycompany.controllers;

import com.mycompany.models.Email;
import com.mycompany.models.User;
import com.mycompany.services.DataHandler;
import com.mycompany.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmailController {

    private EmailService emailService;
    private DataHandler handler;

    @Autowired
    public EmailController(EmailService emailService, DataHandler dataHandler) {
        this.emailService = emailService;
        this.handler = dataHandler;
    }

    @GetMapping("/send-email")
    public String sendEmailForm(Model model) {
        model.addAttribute("email", new Email());
        model.addAttribute("user", new User());
        return "email";
    }

    @PostMapping("/send-email")
    public String sendEmailResult(@ModelAttribute Email email, @ModelAttribute User user, RedirectAttributes attributes) {
        User newUser = handler.searchUser(user);
        if (newUser == null) {
            attributes.addFlashAttribute("message", "There is no such user, enter the data of another user.");
            return "redirect:/send-email";
        }
        emailService.sendSimpleMessage(newUser.getEmail(), email.getSubject(), email.getMessage());

        return "email-result";
    }
}
