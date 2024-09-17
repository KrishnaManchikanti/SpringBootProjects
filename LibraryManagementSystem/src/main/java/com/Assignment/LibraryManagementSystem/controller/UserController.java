package com.Assignment.LibraryManagementSystem.controller;

import com.Assignment.LibraryManagementSystem.dto.UserRequest;
import com.Assignment.LibraryManagementSystem.entity.User;
import com.Assignment.LibraryManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation failed for user request: {}", bindingResult.getAllErrors());
            throw new ValidationException("Validation failed for UserRequest");
        }
        log.info("Adding new user: {}", userRequest);
        userService.addUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable long id) {
        log.info("Updating user with ID: {} with data: {}", id, userRequest);
        userService.updateUser(userRequest, id);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}