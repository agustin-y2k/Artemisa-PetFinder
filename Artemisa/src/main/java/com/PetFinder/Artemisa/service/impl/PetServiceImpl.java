package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.model.Pet;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.model.payloads.PetRequest;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserRequest;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.repository.PetRepository;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.PetService;
import com.PetFinder.Artemisa.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    private ModelMapper modelMapper;


    @Override
    public List<PetResponse> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        List<PetResponse> petResponses = modelMapper.map(pets, List.class);
        return petResponses;
    }

    @Override
    public PetResponse getPetById(Long id) throws Exception {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        PetResponse petResponse = modelMapper.map(pet, PetResponse.class);
        return petResponse;
    }

    @Override
    public void createPet(PetRequest petRequest) {
        Pet pet = modelMapper.map(petRequest, Pet.class);
        petRepository.save(pet);
    }

    @Override
    public void updatePet(PetRequest petRequest, Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        pet = modelMapper.map(petRequest, Pet.class);
        petRepository.save(pet);
    }

    @Override
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        petRepository.delete(pet);
    }

}
