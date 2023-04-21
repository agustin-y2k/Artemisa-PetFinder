package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto getPublicationById(Long id);

    void createPublication(PostDto postDto);

    void updatePublication(PostDto postDto);

    void deletePublication(Long id);

    List<PostDto> getPublicationsByUserEmail(String email);

}
