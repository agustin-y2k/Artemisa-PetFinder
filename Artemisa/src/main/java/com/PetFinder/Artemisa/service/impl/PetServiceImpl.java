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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public List<PetResponse> getAllPets() throws EntityNotFoundException {
        List<Pet> petList = petRepository.findAll();
        if (petList.isEmpty()){
            throw new EntityNotFoundException("Pets not found");
        } else {
            List<PetResponse> petResponseList = new ArrayList<>();
            for (Pet pet : petList) {
                PetResponse petResponse = modelMapper.map(pet, PetResponse.class);
                petResponseList.add(petResponse);
            }
            return petResponseList;
        }
    }

    @Override
    public PetResponse getPetById(Long id) throws EntityNotFoundException {
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        } else {
            Pet petReceived = pet.get();
            PetResponse petResponse = modelMapper.map(petReceived, PetResponse.class);
            return petResponse;
        }
    }


    @Override
    public void createPet(PetRequest petRequest) throws EntityNotFoundException {
        Optional<User> owner = userRepository.findByEmail(petRequest.getOwnerEmail());
        if (!owner.isPresent()){
            throw new EntityNotFoundException("Owner not found");
        } else {
            Pet pet = modelMapper.map(petRequest, Pet.class);
            pet.setOwner(owner.get());
            petRepository.save(pet);
        }
    }

    @Override
    public void updatePet(PetRequest petRequest, Long id) throws EntityNotFoundException {
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        } else {
            Pet petReceived = pet.get();
            User owner = petReceived.getOwner();
            if (owner.getEmail() != petRequest.getOwnerEmail()){
                Optional<User> ownerNew = userRepository.findByEmail(petRequest.getOwnerEmail());
                if (!ownerNew.isPresent()){
                    throw new EntityNotFoundException("Owner not found");
                } else {
                    petReceived.setName(petRequest.getName());
                    petReceived.setPetType(petRequest.getPetType());
                    petReceived.setOwner(owner);
                    petRepository.save(petReceived);
                }
            }
        }
    }

    @Override
    public void deletePet(Long id) throws EntityNotFoundException{
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        } else {
            Pet petReceived = pet.get();
            petRepository.delete(petReceived);
        }
    }

    @Override
    public List<PetResponse> getPetsByOwnerId(Long id) throws EntityNotFoundException {
        Optional<User> owner = userRepository.findById(id);
        if (!owner.isPresent()){
            throw new EntityNotFoundException("Owner not found");
        } else {
            List<Pet> petList = petRepository.findByOwner(owner.get());
            if (petList.isEmpty()){
                throw new EntityNotFoundException("Pets not found");
            } else {
                List<PetResponse> petResponseList = new ArrayList<>();
                for (Pet pet : petList) {
                    PetResponse petResponse = modelMapper.map(pet, PetResponse.class);
                    petResponseList.add(petResponse);
                }
                return petResponseList;
            }
        }
    }

}
