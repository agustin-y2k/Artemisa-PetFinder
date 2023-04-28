package com.PetFinder.Artemisa.controller;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.payloads.PetRequest;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.service.PetService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/api/v1/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // localhost:8080/api/v1/pet/getAll
    @Operation(summary = "Get all pets", description = "Get all pets")
    @GetMapping("/getAll")
    public List<PetResponse> getAllPets() throws EntityNotFoundException{
        return petService.getAllPets();
    }

    // localhost:8080/api/v1/pet/getById/id
    @Operation(summary = "Get pet by id", description = "Get pet by id")
    @GetMapping("/getById/{id}")
    public PetResponse getPetById(@PathVariable Long id) throws EntityNotFoundException{
        return petService.getPetById(id);
    }

    // localhost:8080/api/v1/pet/getByUserEmail/email
    @Operation(summary = "Get pet by user email", description = "Get pet by user email")
    @GetMapping("/getByUserEmail/{email}")
    public List<PetResponse> getPetsByOwnerId(@PathVariable String email) throws EntityNotFoundException{
        return petService.getPetsByOwnerEmail(email);
    }

    // localhost:8080/api/v1/pet/create
    @Operation(summary = "Create pet", description = "Create pet")
    @PostMapping("/create")
    public void createPet(@Valid @RequestBody PetRequest petRequest) throws EntityNotFoundException {
        petService.createPet(petRequest);
    }

    // localhost:8080/api/v1/pet/update/id
    @Operation(summary = "Update pet", description = "Update pet")
    @PutMapping("/update/{id}")
    public void updatePet(@Valid @RequestBody PetRequest petRequest, @PathVariable Long id) throws EntityNotFoundException {
        petService.updatePet(petRequest, id);
    }

    // localhost:8080/api/v1/pet/delete/id
    @Operation(summary = "Delete pet", description = "Delete pet")
    @DeleteMapping("/delete/{id}")
    public void deletePet(@PathVariable Long id) throws EntityNotFoundException {
        petService.deletePet(id);
    }

}
