package com.bruce.jobmatchr.controller;

import com.bruce.jobmatchr.user.User;
import com.bruce.jobmatchr.user.UserNotFoundException;
import com.bruce.jobmatchr.user.UserService;
import com.bruce.jobmatchr.util.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {

        return "forgot_password_form";
    }

    // Create new handler to handle submission of resetting password
    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {

        // Get the email in the email field
        String email = request.getParameter("email");

        // Generate a random string of 45 chars to represent the reset password token
        String token = RandomString.make(45);

        try {
            userService.updateResetPasswordToken(token, email);

            // generate reset password link based on the token
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            System.out.println(resetPasswordLink);

            // send email
            sendEmail(email, resetPasswordLink);

            // Pass in a success message to the form
            model.addAttribute("message", "We have sent  reset password link to your email. Please check.");


        } catch (UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email.");
        }

        System.out.println("Email: " + email);
        System.out.println("Token: " + token);

        return "forgot_password_form";
    }

    // Send the password reset email.
    private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@jobmatchr.com", "JobMatchr Support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";

        String content = "<p> Hello,</p> "
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password.</p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a><b></p>"
                + "<p>Ignore this email if you remembered your password, or you have not made this request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);

    }

    // Show form when user clicks on reset password link so they can change their password
    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value= "token") String token, Model model) {

        // Getting the token when user first requested for a password reset.
        User user = userService.get(token);

        // If no user exists with that token, return an error template.
        if (user == null) {
            model.addAttribute("title", "Reset your Password");
            model.addAttribute("message", "Invalid token");
            return "message";
        }

        // Otherwise, pass the value of the token and return the reset password form
        model.addAttribute("token", token);
        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {

        // Get value of token hidden in the reset_password_form
        String token = request.getParameter("token");

        // Get the value the user entered in the password form in reset_password_form
        String password = request.getParameter("password");

        // Verify that the token we get is the same as in the db
        User user = userService.get(token);

        if (user == null) {
            model.addAttribute("title", "Reset your Password");
            model.addAttribute("message", "Invalid token");
        } else {

            // Update the password if token matches
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password!");
        }

        return "message";

    }

}

