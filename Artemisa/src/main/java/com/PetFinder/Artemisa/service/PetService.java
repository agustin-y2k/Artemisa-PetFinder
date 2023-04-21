package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.model.payloads.PetRequest;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PetService {

    List<PetResponse> getAllPets();

    PetResponse getPetById(Long id) throws Exception;

    void createPet(PetRequest petRequest);

    void updatePet(PetRequest petRequest, Long id);

    void deletePet(Long id);

}
