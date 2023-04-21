package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

        List<UserResponse> getAllUsers();

        UserResponse getUserById(Long id);

        UserResponse getUserByEmail(String email);

        void createUser(UserRequest userRequest);

        void updateUser(UserRequest userRequest, Long id);

        void deleteUser(Long id);

        List<PetResponse> getAllPetsFromUser(String Email);

}
