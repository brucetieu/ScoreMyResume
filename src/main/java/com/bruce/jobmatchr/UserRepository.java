package com.bruce.jobmatchr;

import org.springframework.data.jpa.repository.JpaRepository;

//
public interface UserRepository extends JpaRepository<User, Long> {
}
