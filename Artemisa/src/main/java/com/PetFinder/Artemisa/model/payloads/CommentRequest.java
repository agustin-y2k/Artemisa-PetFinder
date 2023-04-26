package com.PetFinder.Artemisa.model.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Post id is required")
    private Long postId;

    @NotBlank(message = "User id is required")
    @Email(message = "Email should be valid")
    private String userEmail;

}
