package com.PetFinder.Artemisa.model.payloads;

import com.PetFinder.Artemisa.model.PetType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Pet type is mandatory")
    private PetType petType;

    @NotBlank(message = "Owner email is mandatory")
    @Email(message = "Email should be valid")
    private String  ownerEmail;
}
