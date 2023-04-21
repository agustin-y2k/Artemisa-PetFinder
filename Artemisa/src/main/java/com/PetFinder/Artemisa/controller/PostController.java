package com.PetFinder.Artemisa.controller;

import com.PetFinder.Artemisa.dto.PostDto;
import com.PetFinder.Artemisa.service.PostService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/publication")
public class PostController {

    @Autowired
    private PostService postService;

    // localhost:8080/publication/1
    @Operation(summary = "Get publication by id", description = "Get publication by id")
    @GetMapping("/getById/{id}")
    public void getPublicationById(@PathVariable Long id) {
        postService.getPublicationById(id);
    }

    // localhost:8080/publication/create
    @Operation(summary = "Create publication", description = "Create publication")
    @PostMapping("/create")
    public void createPublication(@RequestBody PostDto postDto) {
        postService.createPublication(postDto);
    }

    // localhost:8080/publication/update
    @Operation(summary = "Update publication", description = "Update publication")
    @PutMapping("/update")
    public void updatePublication(@RequestBody PostDto postDto) {
        postService.updatePublication(postDto);
    }

    // localhost:8080/publication/delete/1
    @Operation(summary = "Delete publication", description = "Delete publication")
    @DeleteMapping("/delete/{id}")
    public void deletePublication(@PathVariable Long id) {
        postService.deletePublication(id);
    }

    // localhost:8080/publication/getByUserEmail
    @Operation(summary = "Get publications by user email", description = "Get publications by user email")
    @GetMapping("/getByUserEmail")
    public void getPublicationsByUserEmail(@RequestParam String email) {
        postService.getPublicationsByUserEmail(email);
    }

}
