package com.bruce.jobmatchr;

import org.springframework.data.jpa.repository.JpaRepository;

// 
public interface UserInterface extends JpaRepository<User, Long> {
}
