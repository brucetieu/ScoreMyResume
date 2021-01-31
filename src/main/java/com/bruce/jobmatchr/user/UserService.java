package com.bruce.jobmatchr.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Update password via setting a reset password token by email
     * @param token Password reset token
     * @param email User's email
     * @throws UserNotFoundException handle user not found exception
     */
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {

        // Locate user by email
        User user = userRepository.findByEmail(email);

        // If user is found, pass in the token to set the password reset token, and save it to the db
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any User with email " + email);
        }

    }

    /**
     * Get the customer by the random password token
     * @param resetPasswordToken The randomized password token
     * @return The User with the password token
     */
    public User get(String resetPasswordToken) {

        // Used by controller layer to check if a customer belongs to the given password token or not.
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    /**
     * Update the password of a user
     * @param user The password of the user to be updated
     * @param newPassword The new password the user chooses to set
     */
    public void updatePassword(User user, String newPassword) {
        // Encrypt password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Set the new password with the encoded password
        user.setPassword(encodedPassword);

        // Set the reset password token to null since the user has just changed their password.
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }
}
