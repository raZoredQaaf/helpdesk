package com.fdmgroup.helpdeskapi.service;

import java.util.List;

import com.fdmgroup.helpdeskapi.model.User;

public interface UserService {

	User saveUser(User user);

	List<User> findAllUsers();

	User findUserById(long id);

	void deleteUserById(long id);

	User findUserByUsername(String username);

	List<User> findAllEngineers();

}
