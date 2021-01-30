package com.bruce.jobmatchr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForgotPasswordController {

    @RequestMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {

        return "forgot_password_form";
    }

}

