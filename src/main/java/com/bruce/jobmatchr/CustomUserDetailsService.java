package com.bruce.jobmatchr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Implement the loaduser method. Perform authentication for logging in
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");

        // Otherwise, we authenticate this user
        return new CustomUserDetails(user);

    }

    // Update password via setting a reset password token by email
    public void updateResetPassword(String token, String email) throws UserNotFoundException {
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
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }
}
