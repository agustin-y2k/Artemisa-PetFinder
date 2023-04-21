package com.PetFinder.Artemisa.model.payloads;

import com.PetFinder.Artemisa.model.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PetRequest {

    @NotBlank
    private String name;

    @NotEmpty
    private PetType petType;

    @NotEmpty
    private UserResponse owner;
}
