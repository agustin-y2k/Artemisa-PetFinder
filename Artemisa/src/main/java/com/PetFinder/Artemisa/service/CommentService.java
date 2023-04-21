package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.dto.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDto getCommentById(Long id);

    void createComment(CommentDto commentDto);

    void updateComment(CommentDto commentDto);

    void deleteComment(Long id);
}
