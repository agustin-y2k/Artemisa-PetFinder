package com.PetFinder.Artemisa.model.payloads;

import lombok.Data;

@Data
public class PetResponse {

    private Long id;
    private String name;
    private String petType;
    private UserResponse owner;
}
