package com.ecommerce.miniproject.service.implementation;

import com.ecommerce.miniproject.constant.ErrorMessages;
import com.ecommerce.miniproject.exceptions.Exceptions;
import com.ecommerce.miniproject.model.User;
import com.ecommerce.miniproject.repository.UserRepository;
import com.ecommerce.miniproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServicesImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<User> getUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception exception) {
            LOGGER.error("Error while fetching users", exception);
            throw new Exceptions("Error while fetching users",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            Optional<User> userOptional = Optional.ofNullable(userRepository.findByUserEmail(email));
            return userOptional.orElseThrow(() -> new Exceptions(ErrorMessages.ERROR_USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        } catch (Exception exception) {
            LOGGER.error("Error while fetching user with email {}", email, exception);
            throw new Exceptions("Error while fetching user",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User createUser(User user) {
        try {
            user.setUserActive(true);
            return userRepository.save(user);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when creating user");
            throw new Exceptions("Error ",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User updateUser(String email, User updatedUser) {
        try {
            User user = getUserByEmail(email);
            modelMapper.map(user, updatedUser);
            user.setUserActive(true);
            return userRepository.save(user);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when updating user");
            throw new Exceptions("Error ",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Void deactivateUser(String email) {
        try {
            User user = getUserByEmail(email);
            userRepository.delete(user);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when removing user");
            throw new Exceptions("Error ",
                    HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public Long findUser(User enteredUser) {
        List<User> users = getUsers();
        for (User user: users) {
            if (enteredUser.getUserEmail().equalsIgnoreCase(user.getUserEmail())) {
                return user.getId();
            }
        }
        return null;
    }
}
