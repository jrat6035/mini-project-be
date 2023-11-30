package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.exceptions.Exceptions;
import com.ecommerce.miniproject.model.User;
import com.ecommerce.miniproject.repository.UserRepository;
import com.ecommerce.miniproject.service.implementation.UserServicesImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServicesImpl userServices;

    @Test
    public void testGetUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("test1@example.com"));
        mockUsers.add(new User("test2@example.com"));

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> users = userServices.getUsers();

        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserByEmail() {
        String userEmail = "test@example.com";
        User mockUser = new User(userEmail);

        when(userRepository.findByUserEmail(userEmail)).thenReturn(mockUser);

        User user = userServices.getUserByEmail(userEmail);

        assertEquals(userEmail, user.getUserEmail());
    }

    @Test
    public void testGetUserByEmailNotFound() {
        String userEmail = "nonexistent@example.com";

        when(userRepository.findByUserEmail(userEmail)).thenReturn(null);

        assertThrows(Exceptions.class, () -> userServices.getUserByEmail(userEmail));
    }

    @Test
    public void testCreateUser() {
        User mockUser = new User("test@example.com");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);

        User createdUser = userServices.createUser(mockUser);

        assertEquals(mockUser.getUserEmail(), createdUser.getUserEmail());
    }

    @Test
    public void testUpdateUser() {
        String userEmail = "test@example.com";
        User mockUser = new User(userEmail);
        User updatedUser = new User("updated@example.com");

        when(userRepository.findByUserEmail(userEmail)).thenReturn(mockUser);
        when(modelMapper.map(any(), any())).thenReturn(updatedUser);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        User resultUser = userServices.updateUser(userEmail, updatedUser);

        assertEquals(updatedUser.getUserEmail(), resultUser.getUserEmail());
    }

    @Test
    public void testDeactivateUser() {
        String userEmail = "test@example.com";
        User mockUser = new User(userEmail);

        when(userRepository.findByUserEmail(userEmail)).thenReturn(mockUser);

        userServices.deactivateUser(userEmail);

        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.any(User.class));
    }

    @Test
    public void testFindUser() {
        String userEmail = "test@example.com";
        User enteredUser = new User(userEmail);
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("test1@example.com"));
        mockUsers.add(new User(userEmail));
        mockUsers.add(new User("test2@example.com"));

        when(userRepository.findAll()).thenReturn(mockUsers);

        Long userId = userServices.findUser(enteredUser);

        assertEquals(mockUsers.get(1).getId(), userId);
    }
}