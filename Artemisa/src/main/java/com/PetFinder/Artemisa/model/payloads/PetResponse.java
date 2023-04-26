package com.PetFinder.Artemisa.model.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PetResponse {

    private Long id;
    private String name;
    private String petType;
}
