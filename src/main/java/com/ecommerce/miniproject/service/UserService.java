package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUser(String email, User user);
    Void deactivateUser(String email);
}
