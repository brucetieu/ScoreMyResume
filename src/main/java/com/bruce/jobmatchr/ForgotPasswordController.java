package com.bruce.jobmatchr;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
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

}

