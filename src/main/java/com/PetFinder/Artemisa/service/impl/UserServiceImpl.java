package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.exception.NotAuthorizedException;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() throws EntityNotFoundException {
        List<User> userList = userRepository.findAll().stream().filter(User::isEnabled).toList();
        if (userList.isEmpty()){
            throw new EntityNotFoundException("Users not found");
        }

        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            userResponseList.add(userResponse);
        }
        return userResponseList;
    }

    @Override
    public UserResponse getUserById(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id).filter(User::isEnabled);
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        }
        User userReceived = user.get();
        UserResponse userResponse = modelMapper.map(userReceived, UserResponse.class);
        return userResponse;
    }

    @Override
    public UserResponse getUserByEmail(String email) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByEmail(email).filter(User::isEnabled);
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        } else {
            User userReceived = user.get();
            UserResponse userResponse = modelMapper.map(userReceived, UserResponse.class);
            return userResponse;
        }
    }

    @Override
    public void updateUser(UserRequest userRequest, String email) throws EntityNotFoundException, NotAuthorizedException {
        Optional<User> user = userRepository.findByEmail(email).filter(User::isEnabled);

        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUser = userDetails.getUsername();

        if (!emailUser.equals(email)){
            throw new IllegalArgumentException("You can't update this user");
        }

        User userExists = userRepository.findByEmail(userRequest.getEmail()).orElse(null);
        if (userExists != null && !userExists.getEmail().equals(email)) {
            throw new IllegalArgumentException("User already exists");
        }

        User userReceived = user.get();
        userReceived.setFirstname(userRequest.getFirstname());
        userReceived.setLastname(userRequest.getLastname());
        userRepository.save(userReceived);
        }

    @Override
    public void deleteUser(String email) throws EntityNotFoundException, NotAuthorizedException {
        Optional<User> user = userRepository.findByEmail(email).filter(User::isEnabled);

        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUser = userDetails.getUsername();

        if (emailUser.equals(email) || authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
            userRepository.delete(user.get());
        } else {
            throw new IllegalArgumentException("You can't delete this user");
        }
    }

}
