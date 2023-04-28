package com.PetFinder.Artemisa.controller;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // localhost:8080/api/v1/user/getAll
    @Operation(summary = "Get all users", description = "Get all users")
    @GetMapping("/getAll")
    public List<UserResponse> getAllUsers() throws EntityNotFoundException{
        return userService.getAllUsers();
    }

    // localhost:8080/api/v1/user/getById/id
    @Operation(summary = "Get user by id", description = "Get user by id")
    @GetMapping("/getById/{id}")
    public UserResponse getUserById(@PathVariable Long id) throws EntityNotFoundException {
        return userService.getUserById(id);
    }

    // localhost:8080/api/v1/user/getByEmail/email
    @Operation(summary = "Get user by email", description = "Get user by email")
    @GetMapping("/getByEmail/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) throws EntityNotFoundException {
        return userService.getUserByEmail(email);
    }

    // localhost:8080/api/v1/user/update/email
    @Operation(summary = "Update user", description = "Update user")
    @PutMapping("/update/{email}")
    public void updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable String email) throws EntityNotFoundException {
        userService.updateUser(userRequest, email);
    }

    // localhost:8080/api/v1/user/delete/email
    @Operation(summary = "Delete user", description = "Delete user")
    @DeleteMapping("/delete/{email}")
    public void deleteUser(@PathVariable String email) throws EntityNotFoundException {
        userService.deleteUser(email);
    }

}
