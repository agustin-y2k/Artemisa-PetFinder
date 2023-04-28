package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.Comment;
import com.PetFinder.Artemisa.model.Post;
import com.PetFinder.Artemisa.model.payloads.CommentRequest;
import com.PetFinder.Artemisa.model.payloads.CommentResponse;
import com.PetFinder.Artemisa.repository.CommentRepository;
import com.PetFinder.Artemisa.repository.PostRepository;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @Override
        public List<CommentResponse> getAllComments() throws EntityNotFoundException {

            List<Comment> commentList = commentRepository.findAll();
            if (commentList.isEmpty()){
                throw new EntityNotFoundException("Comments not found");
            }

            List<CommentResponse> commentResponseList = new ArrayList<>();
            for (Comment comment : commentList) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
                commentResponseList.add(commentResponse);
            }
            return commentResponseList;
        }

        @Override
        public CommentResponse getCommentById(Long id) throws EntityNotFoundException {

            Optional<Comment> comment = commentRepository.findById(id);
            if (!comment.isPresent()){
                throw new EntityNotFoundException("Comment not found");
            }

            Comment commentReceived = comment.get();
            CommentResponse commentResponse = modelMapper.map(commentReceived, CommentResponse.class);
            return commentResponse;
        }

        @Override
        public List<CommentResponse> getCommentsByPostId(Long id) throws EntityNotFoundException {

            Optional<Post> post = postRepository.findById(id);
            if (!post.isPresent()){
                throw new EntityNotFoundException("Post not found");
            }

            List<Comment> commentList = postRepository.findById(id).get().getComments();
            if (commentList.isEmpty()){
                throw new EntityNotFoundException("Comments not found");
            }

            List<CommentResponse> commentResponseList = new ArrayList<>();
            for (Comment comment : commentList) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
                commentResponseList.add(commentResponse);
            }
            return commentResponseList;
        }

        @Override
        public void createComment(CommentRequest commentRequest) throws EntityNotFoundException {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String emailUser = userDetails.getUsername();

            if (!userRepository.findByEmail(emailUser).isPresent()){
                throw new EntityNotFoundException("User not found");
            }

            Optional<Post> post = postRepository.findById(commentRequest.getPostId());
            if (!post.isPresent()){
                throw new EntityNotFoundException("Post not found");
            }

            Comment comment = modelMapper.map(commentRequest, Comment.class);
            comment.setUser(userRepository.findByEmail(emailUser).get());
            comment.setPost(post.get());
            commentRepository.save(comment);
        }

        @Override
        public void updateComment(CommentRequest commentRequest, Long id) throws EntityNotFoundException {

            Optional<Comment> comment = commentRepository.findById(id);
            if (!comment.isPresent()){
                throw new EntityNotFoundException("Comment not found");
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String emailUser = userDetails.getUsername();


            if (comment.get().getUser().getEmail().equals(emailUser) || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){

                Optional<Post> post = postRepository.findById(commentRequest.getPostId());
                if (!post.isPresent()){
                    throw new EntityNotFoundException("Post not found");
                }

                Comment commentReceived = comment.get();
                commentReceived.setContent(commentRequest.getContent());
                commentReceived.setPost(post.get());
                commentRepository.save(commentReceived);
            }
        }

        @Override
        public void deleteComment(Long id) throws EntityNotFoundException {

            Optional<Comment> comment = commentRepository.findById(id);
            if (!comment.isPresent()){
                throw new EntityNotFoundException("Comment not found");
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String emailUser = userDetails.getUsername();

            if (comment.get().getUser().getEmail().equals(emailUser) || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
                commentRepository.deleteById(id);
            } else {
                throw new EntityNotFoundException("You are not allowed to delete this comment");
            }
        }
}
