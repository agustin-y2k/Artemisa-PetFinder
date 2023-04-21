package com.PetFinder.Artemisa.controller;

import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // localhost:8080/user/getAll
    @Operation(summary = "Get all users", description = "Get all users")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    // localhost:8080/user/getById/1
    @Operation(summary = "Get user by id", description = "Get user by id")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/getById/{id}")
    public UserResponse getUserById(@PathVariable Long id) throws Exception {
        return userService.getUserById(id);
    }

    // localhost:8080/user/getByEmail/email
    @Operation(summary = "Get user by email", description = "Get user by email")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/getByEmail/email")
    public UserResponse getUserByEmail(@RequestParam String email) throws Exception {
        return userService.getUserByEmail(email);
    }

    // localhost:8080/user/create
    @Operation(summary = "Create user", description = "Create user")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @PostMapping("/create")
    public void createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
    }

    // localhost:8080/user/update
    @Operation(summary = "Update user", description = "Update user")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @PutMapping("/update")
    public void updateUser(@RequestBody UserRequest userRequest, @RequestParam Long id) throws Exception {
        userService.updateUser(userRequest, id);
    }

    // localhost:8080/user/delete/1
    @Operation(summary = "Delete user", description = "Delete user")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
    }

    // localhost:8080/user/{email}/pets
    @Operation(summary = "Get all pets by user email", description = "Get all pets by user email")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/{email}/pets")
    public List<PetResponse> getAllPetsByUserEmail(@PathVariable String email) throws Exception {
        return userService.getAllPetsFromUser(email);
    }

}
