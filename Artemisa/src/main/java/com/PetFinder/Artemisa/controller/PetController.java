package com.PetFinder.Artemisa.controller;

import com.PetFinder.Artemisa.model.payloads.PetRequest;
import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import com.PetFinder.Artemisa.service.PetService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    // localhost:8080/pets
    @Operation(summary = "Get all pets", description = "Get all pets")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/getAll")
    public List<PetResponse> getAllPets() {
        return petService.getAllPets();
    }

    // localhost:8080/pet/1
    @Operation(summary = "Get pet by id", description = "Get pet by id")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/getById/{id}")
    public PetResponse getPetById(@PathVariable Long id) throws Exception{
        return petService.getPetById(id);
    }

    // localhost:8080/pet/create
    @Operation(summary = "Create pet", description = "Create pet")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @PostMapping("/create")
    public void createPet(@RequestBody PetRequest petRequest) { petService.createPet(petRequest); }

    // localhost:8080/pet/update
    @Operation(summary = "Update pet", description = "Update pet")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @PutMapping("/update")
    public void updatePet(@RequestBody PetRequest petRequest, @RequestParam Long id) throws Exception {
        petService.updatePet(petRequest, id);
    }

    // localhost:8080/pet/delete
    @Operation(summary = "Delete pet", description = "Delete pet")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @DeleteMapping("/delete/{id}")
    public void deletePet(@PathVariable Long id) throws Exception {
        petService.deletePet(id);
    }

}
