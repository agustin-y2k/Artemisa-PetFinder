package com.PetFinder.Artemisa.service;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.payloads.CommentRequest;
import com.PetFinder.Artemisa.model.payloads.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    List<CommentResponse> getAllComments() throws EntityNotFoundException;

    CommentResponse getCommentById(Long id) throws EntityNotFoundException;

    List<CommentResponse> getCommentsByPostId(Long id) throws EntityNotFoundException;

    void createComment(CommentRequest commentRequest) throws EntityNotFoundException;

    void updateComment(CommentRequest commentRequest, Long id) throws EntityNotFoundException;

    void deleteComment(Long id) throws EntityNotFoundException;
}
