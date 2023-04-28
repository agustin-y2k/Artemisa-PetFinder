package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.payloads.PostRequest;
import com.PetFinder.Artemisa.model.payloads.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    List<PostResponse> getAllPosts() throws EntityNotFoundException;
    PostResponse getPostById(Long id) throws EntityNotFoundException;

    void createPost(PostRequest postRequest) throws EntityNotFoundException;

    void updatePost(PostRequest postRequest, Long id) throws EntityNotFoundException;

    void deletePost(Long id) throws EntityNotFoundException;

    List<PostResponse> getPostsByUserEmail(String email) throws EntityNotFoundException;

    PostResponse getPostsByPetId(Long Id) throws EntityNotFoundException;


}
