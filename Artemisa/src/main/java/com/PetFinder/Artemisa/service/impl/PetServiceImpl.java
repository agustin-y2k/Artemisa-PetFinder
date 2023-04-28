package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.model.Pet;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.model.payloads.PetRequest;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.repository.PetRepository;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.PetService;
import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    
    private final UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public PetServiceImpl(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<PetResponse> getAllPets() throws EntityNotFoundException {

        List<Pet> petList = petRepository.findAll();
        if (petList.isEmpty()){
            throw new EntityNotFoundException("Pets not found");
        }

        List<PetResponse> petResponseList = new ArrayList<>();
        for (Pet pet : petList) {
            PetResponse petResponse = modelMapper.map(pet, PetResponse.class);
            petResponseList.add(petResponse);
        }
        return petResponseList;
    }

    @Override
    public PetResponse getPetById(Long id) throws EntityNotFoundException {

        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        }

        Pet petReceived = pet.get();
        PetResponse petResponse = modelMapper.map(petReceived, PetResponse.class);
        return petResponse;
    }


    @Override
    public void createPet(PetRequest petRequest) throws EntityNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUser = userDetails.getUsername();

        if (!userRepository.findByEmail(emailUser).isPresent()){
            throw new EntityNotFoundException("Owner not found");
        }

        Pet pet = modelMapper.map(petRequest, Pet.class);
        pet.setOwner(userRepository.findByEmail(emailUser).get());
        petRepository.save(pet);
    }

    @Override
    public void updatePet(PetRequest petRequest, Long id) throws EntityNotFoundException {

        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        }

        Pet petReceived = pet.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUser = userDetails.getUsername();

        if (petReceived.getOwner().getEmail().equals(emailUser) || authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
            petReceived.setName(petRequest.getName());
            petReceived.setPetType(petRequest.getPetType());
            petRepository.save(petReceived);
        } else {
            throw new EntityNotFoundException("You are not the owner of this pet");
        }
    }

    @Override
    public void deletePet(Long id) throws EntityNotFoundException{

        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUser = userDetails.getUsername();

        if (pet.get().getOwner().getEmail().equals(emailUser) || authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
            Pet petReceived = pet.get();
            petRepository.delete(petReceived);
        } else {
            throw new EntityNotFoundException("You are not the owner of this pet");
        }
    }

    @Override
    public List<PetResponse> getPetsByOwnerEmail(String email) throws EntityNotFoundException {

        Optional<User> owner = userRepository.findByEmail(email);
        if (!owner.isPresent()){
            throw new EntityNotFoundException("Owner not found");
        }

        List<Pet> petList = petRepository.findByOwner(owner.get());
        if (petList.isEmpty()){
            throw new EntityNotFoundException("Pets not found");
        }

        List<PetResponse> petResponseList = new ArrayList<>();
        for (Pet pet : petList) {
            PetResponse petResponse = modelMapper.map(pet, PetResponse.class);
            petResponseList.add(petResponse);
        }

        return petResponseList;
        }

}
