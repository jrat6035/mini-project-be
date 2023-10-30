package com.ecommerce.miniproject.controller;

import com.ecommerce.miniproject.dto.ResponseObject;
import com.ecommerce.miniproject.dto.UserDTO;
import com.ecommerce.miniproject.model.User;
import com.ecommerce.miniproject.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.path.users}")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ResponseObject> getUsers() {
        return sendFoundResponse(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserDTO userDTO) {
        User createdUser = modelMapper.map(userDTO, User.class);
        return sendCreatedResponse(userService.createUser(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable String id) {
        return sendFoundResponse(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
        User updatedUser = modelMapper.map(userDTO, User.class);
        return sendSuccessResponse(userService.updateUser(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deactivateUser(@PathVariable String id) {
        return sendSuccessResponse(userService.deactivateUser(id));
    }
}
