package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

        List<UserResponse> getAllUsers() throws EntityNotFoundException;

        UserResponse getUserById(Long id) throws EntityNotFoundException;

        UserResponse getUserByEmail(String email) throws EntityNotFoundException;

        void createUser(UserRequest userRequest);

        void updateUser(UserRequest userRequest, Long id) throws EntityNotFoundException;

        void deleteUser(Long id) throws EntityNotFoundException;


}
