package com.PetFinder.Artemisa.model.payloads;

import com.PetFinder.Artemisa.model.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

        @NotBlank(message = "Title is mandatory")
        private String title;

        @NotBlank(message = "Content is mandatory")
        private String content;

        @NotBlank(message = "Image is mandatory")
        private String image;

        @NotNull(message = "Pet type is mandatory")
        private PostType postType;

        @NotNull(message = "Pet is mandatory")
        private Long petId;


}
