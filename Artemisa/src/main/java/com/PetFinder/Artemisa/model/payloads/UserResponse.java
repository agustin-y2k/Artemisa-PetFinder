package com.PetFinder.Artemisa.model.payloads;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
}
