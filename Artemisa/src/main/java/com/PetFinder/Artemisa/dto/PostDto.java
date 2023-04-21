package com.PetFinder.Artemisa.dto;

import com.PetFinder.Artemisa.model.payloads.PetResponse;
import com.PetFinder.Artemisa.model.payloads.UserResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {

    private Long id;

    @NotBlank
    private String content;

    @NotEmpty
    private UserResponse user;

    @NotEmpty
    private PetResponse pet;

    private Set<CommentDto> comments = new HashSet<>();
}
