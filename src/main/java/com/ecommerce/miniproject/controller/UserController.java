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
    public ResponseEntity<ResponseObject> createUser(@RequestBody User user) {
        return sendCreatedResponse(userService.createUser(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable String email) {
        return sendFoundResponse(userService.getUserByEmail(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable String email, @Valid @RequestBody User user) {
        return sendSuccessResponse(userService.updateUser(email, user));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ResponseObject> deactivateUser(@PathVariable String email) {
        return sendSuccessResponse(userService.deactivateUser(email));
    }
}
