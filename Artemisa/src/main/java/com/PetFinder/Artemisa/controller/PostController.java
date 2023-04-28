package com.PetFinder.Artemisa.controller;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.payloads.PostRequest;
import com.PetFinder.Artemisa.model.payloads.PostResponse;
import com.PetFinder.Artemisa.service.PostService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // localhost:8080/api/v1/post/getAll
    @Operation(summary = "Get all post", description = "Get all post")
    @GetMapping("/getAll")
    public List<PostResponse> getAllPosts() throws EntityNotFoundException {
        return postService.getAllPosts();
    }

    // localhost:8080/api/v1/post/getById/id
    @Operation(summary = "Get post by id", description = "Get post by id")
    @GetMapping("/getById/{id}")
    public PostResponse getPostById(@PathVariable Long id) throws EntityNotFoundException {
        return postService.getPostById(id);
    }

    // localhost:8080/api/v1/post/create
    @Operation(summary = "Create post", description = "Create post")
    @PostMapping("/create")
    public void createPost(@Valid @RequestBody PostRequest postRequest) throws EntityNotFoundException {
        postService.createPost(postRequest);
    }

    // localhost:8080/api/v1/post/update/id
    @Operation(summary = "Update post", description = "Update post")
    @PutMapping("/update/{id}")
    public void updatePost(@Valid @RequestBody PostRequest postRequest, @PathVariable Long id) throws EntityNotFoundException {
        postService.updatePost(postRequest, id);
    }

    // localhost:8080/api/v1/post/delete/id
    @Operation(summary = "Delete post", description = "Delete post")
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Long id) throws EntityNotFoundException {
        postService.deletePost(id);
    }

    // localhost:8080/api/v1/post/getByUserEmail/email
    @Operation(summary = "Get post by user email", description = "Get post by user email")
    @GetMapping("/getByUserEmail/{email}")
    public List<PostResponse> getPostsByUserEmail(@PathVariable String email) throws EntityNotFoundException {
        return postService.getPostsByUserEmail(email);
    }

    // localhost:8080/api/v1/post/getByPetId/id
    @Operation(summary = "Get post by pet id", description = "Get post by pet id")
    @GetMapping("/getByPetId/{id}")
    public PostResponse getPostsByPetId(@PathVariable Long id) throws EntityNotFoundException {
        return postService.getPostsByPetId(id);
    }



}
