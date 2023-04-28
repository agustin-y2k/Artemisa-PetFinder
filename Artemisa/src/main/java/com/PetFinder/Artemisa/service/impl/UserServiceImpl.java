package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.Pet;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.repository.PetRepository;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetServiceImpl petService;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<UserResponse> getAllUsers() throws EntityNotFoundException {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()){
            throw new EntityNotFoundException("Users not found");
        } else {
            List<UserResponse> userResponseList = new ArrayList<>();
            for (User user : userList) {
                UserResponse userResponse = modelMapper.map(user, UserResponse.class);
                userResponseList.add(userResponse);
            }
            return userResponseList;
        }
    }

    @Override
    public UserResponse getUserById(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        } else {
            User userReceived = user.get();
            UserResponse userResponse = modelMapper.map(userReceived, UserResponse.class);
            return userResponse;
        }
    }

    @Override
    public UserResponse getUserByEmail(String email) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        } else {
            User userReceived = user.get();
            UserResponse userResponse = modelMapper.map(userReceived, UserResponse.class);
            return userResponse;
        }
    }

    @Override
    public void updateUser(UserRequest userRequest, Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        } else {
            User userExists = userRepository.findByEmail(userRequest.getEmail()).orElse(null);
            if (userExists != null && !userExists.getId().equals(id)){
                throw new IllegalArgumentException("User already exists");
            }
            User userReceived = user.get();
            userReceived.setFirstname(userRequest.getFirstname());
            userReceived.setLastname(userRequest.getLastname());
            userReceived.setEmail(userRequest.getEmail());
            userReceived.setPassword(userRequest.getPassword());
            userRepository.save(userReceived);
        }
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        } else {
            userRepository.delete(user.get());
        }
    }

}
