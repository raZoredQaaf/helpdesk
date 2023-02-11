package com.fdmgroup.helpdeskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.helpdeskapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
