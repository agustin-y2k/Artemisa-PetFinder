package com.PetFinder.Artemisa.model.payloads;

import lombok.Data;

@Data
public class PostResponse {

        private Long id;
        private String title;
        private String content;
        private String image;
        private String createDate;
}
