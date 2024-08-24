package com.Assignment.LibraryManagementSystem.service;

import com.Assignment.LibraryManagementSystem.dto.UserRequest;
import com.Assignment.LibraryManagementSystem.entity.User;
import com.Assignment.LibraryManagementSystem.exceptions.BookAlreadyBorrowedException;
import com.Assignment.LibraryManagementSystem.exceptions.UserNotFoundException;
import com.Assignment.LibraryManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * Adds a new user to the system.
     */
    public void addUser(UserRequest userRequest) {
        User user = User.builder()
                .phNo(userRequest.getPhNo())
                .address(userRequest.getAddress())
                .bookList(userRequest.getBookList())
                .build();

        user.setName(userRequest.getName());
        userRepository.save(user);
        log.info("Added new user with ID {}", user.getId());
    }

    /**
     * Retrieves all users from the system.
     */
    public List<User> getAllUsers() {
        log.info("Fetched all users");
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by ID or throws an exception if not found.
     */
    private User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", userId);
                    return new UserNotFoundException("User not found with id " + userId);
                });
    }

    /**
     * Updates an existing user's details.
     */
    public void updateUser(UserRequest userRequest, long userId) {
        User user = getUserById(userId);

        if (userRequest.getAddress() != null)
            user.setAddress(userRequest.getAddress());

        if (userRequest.getName() != null)
            user.setName(userRequest.getName());

        if (userRequest.getPhNo() != null)
            user.setPhNo(userRequest.getPhNo());

        if (userRequest.getBookList() != null)
            user.setBookList(userRequest.getBookList());

        userRepository.save(user);
        log.info("Updated user with ID {}", userId);
    }

    /**
     * Deletes a user from the system.
     */
    public void deleteUser(long userId) {
        User user = getUserById(userId);

        if (!user.getBookList().isEmpty()) {
            log.warn("User with ID {} cannot be deleted as they have borrowed books", userId);
            throw new BookAlreadyBorrowedException("User cannot be deleted as they have borrowed books");
        }

        userRepository.deleteById(userId);
        log.info("Deleted user with ID {}", userId);
    }

}
