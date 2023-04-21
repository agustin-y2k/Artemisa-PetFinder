package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.model.Pet;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            userResponseList.add(userResponse);
        }
        if (userResponseList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found");
        }
        return userResponseList;
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }

    @Override
    public void createUser(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserRequest userRequest, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user = modelMapper.map(userRequest, User.class);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public List<PetResponse> getAllPetsFromUser(String email) {
        User user = userRepository.findByEmail(email);
        List<Pet> pets = (List<Pet>) user.getPets();
        List<PetResponse> petResponses = modelMapper.map(pets, List.class);
        return petResponses;
    }

}
