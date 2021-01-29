package com.bruce.jobmatchr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
}
