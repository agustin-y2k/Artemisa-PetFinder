package com.PetFinder.Artemisa.dto;

import com.PetFinder.Artemisa.model.payloads.UserResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    @NotBlank
    private String content;

    @NotEmpty
    private UserResponse user;

    @NotEmpty
    private PostDto publication;
}
