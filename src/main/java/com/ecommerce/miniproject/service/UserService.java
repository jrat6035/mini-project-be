package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(String id);
    User createUser(User user);
    User updateUser(String id, User user);
    Void deactivateUser(String id);
}
