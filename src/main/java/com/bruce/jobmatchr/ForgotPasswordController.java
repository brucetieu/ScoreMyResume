package com.bruce.jobmatchr;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {

    @RequestMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {

        return "forgot_password_form";
    }

    // Create new handler to handle submission of resetting password
    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request) {

        // Get the email in the email field
        String email = request.getParameter("email");

        // Generate a random string of 45 chars to represent the reset password token
        String token = RandomString.make(45);

        System.out.println("Email: " + email);
        System.out.println("Token: " + token);

        return "forgot_password_form";
    }

}

