package com.PetFinder.Artemisa.model.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    private String email;
}
