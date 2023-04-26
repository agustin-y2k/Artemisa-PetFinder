package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.model.payloads.PetRequest;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PetService {

    List<PetResponse> getAllPets() throws EntityNotFoundException;

    PetResponse getPetById(Long id) throws EntityNotFoundException;

    List<PetResponse> getPetsByOwnerId(Long id) throws EntityNotFoundException;

    void createPet(PetRequest petRequest) throws EntityNotFoundException;

    void updatePet(PetRequest petRequest, Long id) throws EntityNotFoundException;

    void deletePet(Long id) throws EntityNotFoundException;

}
