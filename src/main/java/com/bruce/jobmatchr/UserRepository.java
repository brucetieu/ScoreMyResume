package com.bruce.jobmatchr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Run this query every time we find user by email
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    // Find a user by the password token
    User findByResetPasswordToken(String token);
}
