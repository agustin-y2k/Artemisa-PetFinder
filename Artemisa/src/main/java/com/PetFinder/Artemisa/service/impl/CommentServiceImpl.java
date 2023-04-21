package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.dto.CommentDto;
import com.PetFinder.Artemisa.model.Comment;
import com.PetFinder.Artemisa.repository.CommentRepository;
import com.PetFinder.Artemisa.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

        @Override
        public CommentDto getCommentById(Long id) {
            Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
            CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
            return commentDto;
        }

        @Override
        public void createComment(CommentDto commentDto) {
            Comment comment = modelMapper.map(commentDto, Comment.class);
            commentRepository.save(comment);
        }

        @Override
        public void updateComment(CommentDto commentDto) {
            Comment comment = modelMapper.map(commentDto, Comment.class);
            commentRepository.save(comment);
        }

        @Override
        public void deleteComment(Long id) {
            Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
            commentRepository.delete(comment);
        }
}
