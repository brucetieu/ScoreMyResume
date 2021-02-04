package com.bruce.jobmatchr.controller;


import com.bruce.jobmatchr.user.User;
import com.bruce.jobmatchr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String viewHomepage() {
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user, RedirectAttributes redirAttrs) throws SQLIntegrityConstraintViolationException{

        // Save the registered user in the db
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // If a duplicate entry exists in db, flash a message.
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return "redirect:/register?error";
        }


        return "registration_success";
    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/get_started")
    public String viewGatheringInfo(Model model) {


        return "get_started";
    }
}
