package com.bruce.jobmatchr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Update password via setting a reset password token by email
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setResetPasswordToken(token);
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

        // Set the reset password token to null since the user has just changed their password.
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }
}
