package com.bruce.jobmatchr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Update password via setting a reset password token
    public void updateResetPassword(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setReset_password_token(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any User with email " + email);
        }

    }

    // Get the customer by the random password token
    public User get(String resetPasswordToken) {

        // Used by controller layer to check if a customer belongs to the given password token or not.
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    // Update the password of a user
    public void updatePassword(User user, String newPassword) {
        // Encrypt password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setReset_password_token(null);

        userRepository.save(user);
    }




}