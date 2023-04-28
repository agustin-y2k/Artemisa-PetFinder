package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

        List<UserResponse> getAllUsers() throws EntityNotFoundException;

        UserResponse getUserById(Long id) throws EntityNotFoundException;

        UserResponse getUserByEmail(String email) throws EntityNotFoundException;

        void updateUser(UserRequest userRequest, String email) throws EntityNotFoundException;

        void deleteUser(String email) throws EntityNotFoundException;


}
