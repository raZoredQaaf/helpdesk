package com.fdmgroup.helpdeskapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.helpdeskapi.model.Admin;
import com.fdmgroup.helpdeskapi.model.Client;
import com.fdmgroup.helpdeskapi.model.Engineer;
import com.fdmgroup.helpdeskapi.model.User;
import com.fdmgroup.helpdeskapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@NonNull
	private UserService userService;

	@Operation(summary = "Save an admin")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "User created"), })
	@PostMapping("/admin")
	public ResponseEntity<?> saveAdmin(@RequestBody Admin admin) {
		logger.info("Saving admin: {}", admin);
		return new ResponseEntity<>(userService.saveUser(admin), HttpStatus.CREATED);
	}

	@Operation(summary = "Save an engineer")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "User created"), })
	@PostMapping("/engineer")
	public ResponseEntity<?> saveEngineer(@RequestBody Engineer engineer) {
		logger.info("Saving engineer: {}", engineer);
		return new ResponseEntity<>(userService.saveUser(engineer), HttpStatus.CREATED);
	}

	@Operation(summary = "Save a client")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "User created"), })
	@PostMapping("/client")
	public ResponseEntity<?> saveClient(@RequestBody Client client) {
		logger.info("Saving client: {}", client);
		return new ResponseEntity<>(userService.saveUser(client), HttpStatus.CREATED);
	}

	@Operation(summary = "Find all users")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Users found"), })
	@GetMapping
	public ResponseEntity<?> findAllUsers() {
		logger.info("Finding all users");
		List<User> users = userService.findAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@Operation(summary = "Find all engineers")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Engineers found"), })
	@GetMapping("/engineers")
	public ResponseEntity<?> findAllEngineers() {
		logger.info("Finding all engineers");
		List<User> users = userService.findAllEngineers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@Operation(summary = "Find a user by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User found"),
			@ApiResponse(responseCode = "404", description = "User not found"), })
	@GetMapping("/{id}")
	public ResponseEntity<?> findUserById(@PathVariable long id) {
		if (userService.findUserById(id) != null) {
			logger.info("Finding user with id: {}", id);
			return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
		} else {
			logger.warn("User with id: {} not found", id);
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Find a user by username")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User found"),
			@ApiResponse(responseCode = "404", description = "User not found"), })
	@GetMapping("/authenticate/{username}/{password}")
	public ResponseEntity<?> findUserByUsername(@PathVariable String username, @PathVariable String password) {
		User returnedUser = userService.findUserByUsername(username);
		if (returnedUser != null) {
			if (returnedUser.getPassword().equals(password)) {
				logger.info("Finding user with username: {}", username);
				return new ResponseEntity<>(returnedUser, HttpStatus.OK);
			} else {
				logger.warn("Username and password: {} do not match", username);
				return new ResponseEntity<>("Username and password do not match", HttpStatus.NOT_FOUND);
			}
		} else {
			logger.warn("User with username: {} not found", username);
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Delete a user by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User deleted"),
			@ApiResponse(responseCode = "404", description = "User not found"), })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable long id) {
		if (userService.findUserById(id) != null) {
			logger.info("Deleting user with id: {}", id);
			userService.deleteUserById(id);
			return new ResponseEntity<>("User deleted", HttpStatus.OK);
		} else {
			logger.warn("User with id: {} not found", id);
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}
}
