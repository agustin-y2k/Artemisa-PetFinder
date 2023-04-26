package com.PetFinder.Artemisa.controller;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.payloads.CommentRequest;
import com.PetFinder.Artemisa.model.payloads.CommentResponse;
import com.PetFinder.Artemisa.service.CommentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // localhost:8080/comment/getAll
    @Operation(summary = "Get all comments", description = "Get all comments")
    @GetMapping("/getAll")
    public List<CommentResponse> getAllComments() throws EntityNotFoundException {
        return commentService.getAllComments();
    }

    // localhost:8080/comment/getById/1
    @Operation(summary = "Get comment by id", description = "Get comment by id")
    @GetMapping("/getById/{id}")
    public CommentResponse getCommentById(@PathVariable Long id) throws EntityNotFoundException {
        return commentService.getCommentById(id);
    }

    // localhost:8080/comment/getByPostId/1
    @Operation(summary = "Get comments by post id", description = "Get comments by post id")
    @GetMapping("/getByPostId/{id}")
    public List<CommentResponse> getCommentsByPostId(@PathVariable Long id) throws EntityNotFoundException {
        return commentService.getCommentsByPostId(id);
    }

    // localhost:8080/comment/create
    @Operation(summary = "Create comment", description = "Create comment")
    @PostMapping("/create")
    public void createComment(@Valid @RequestBody CommentRequest commentRequest) throws EntityNotFoundException {
        commentService.createComment(commentRequest);
    }

    // localhost:8080/comment/update/1
    @Operation(summary = "Update comment", description = "Update comment")
    @PutMapping("/update/{id}")
    public void updateComment(@Valid @RequestBody CommentRequest commentRequest, @PathVariable Long id) throws EntityNotFoundException {
        commentService.updateComment(commentRequest, id);
    }

    // localhost:8080/comment/delete/1
    @Operation(summary = "Delete comment", description = "Delete comment")
    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id) throws EntityNotFoundException {
        commentService.deleteComment(id);
    }

}
