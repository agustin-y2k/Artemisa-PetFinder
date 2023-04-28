package com.PetFinder.Artemisa.model.payloads;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private String createDate;
    private UserResponse user;
}
