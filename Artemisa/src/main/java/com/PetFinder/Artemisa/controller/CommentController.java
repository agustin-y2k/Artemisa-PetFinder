package com.PetFinder.Artemisa.controller;

import com.PetFinder.Artemisa.dto.CommentDto;
import com.PetFinder.Artemisa.service.CommentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // localhost:8080/comment/1
    @Operation(summary = "Get comment by id", description = "Get comment by id")
    @GetMapping("/getById/{id}")
    public void getCommentById(@PathVariable Long id) {
        commentService.getCommentById(id);
    }

    // localhost:8080/comment/create
    @Operation(summary = "Create comment", description = "Create comment")
    @PostMapping("/create")
    public void createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
    }

    // localhost:8080/comment/update
    @Operation(summary = "Update comment", description = "Update comment")
    @PutMapping("/update")
    public void updateComment(@RequestBody CommentDto commentDto) {
        commentService.updateComment(commentDto);
    }

    // localhost:8080/comment/delete/1
    @Operation(summary = "Delete comment", description = "Delete comment")
    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
