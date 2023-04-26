package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.Comment;
import com.PetFinder.Artemisa.model.Post;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.model.payloads.CommentRequest;
import com.PetFinder.Artemisa.model.payloads.CommentResponse;
import com.PetFinder.Artemisa.repository.CommentRepository;
import com.PetFinder.Artemisa.repository.PostRepository;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private PostRepository postRepository;


    @Override
        public List<CommentResponse> getAllComments() throws EntityNotFoundException {
            List<Comment> commentList = commentRepository.findAll();
            if (commentList.isEmpty()){
                throw new EntityNotFoundException("Comments not found");
            } else {
                List<CommentResponse> commentResponseList = new ArrayList<>();
                for (Comment comment : commentList) {
                    CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
                    commentResponseList.add(commentResponse);
                }
                return commentResponseList;
            }
        }

        @Override
        public CommentResponse getCommentById(Long id) throws EntityNotFoundException {
            Optional<Comment> comment = commentRepository.findById(id);
            if (!comment.isPresent()){
                throw new EntityNotFoundException("Comment not found");
            } else {
                Comment commentReceived = comment.get();
                CommentResponse commentResponse = modelMapper.map(commentReceived, CommentResponse.class);
                return commentResponse;
            }
        }

        @Override
        public List<CommentResponse> getCommentsByPostId(Long id) throws EntityNotFoundException {
            Optional<Post> post = postRepository.findById(id);
            if (!post.isPresent()){
                throw new EntityNotFoundException("Post not found");
            } else {
                List<Comment> commentList = postRepository.findById(id).get().getComments();
                if (commentList.isEmpty()){
                    throw new EntityNotFoundException("Comments not found");
                } else {
                    List<CommentResponse> commentResponseList = new ArrayList<>();
                    for (Comment comment : commentList) {
                        CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
                        commentResponseList.add(commentResponse);
                    }
                    return commentResponseList;
                }
            }
        }

        @Override
        public void createComment(CommentRequest commentRequest) throws EntityNotFoundException {
            Optional<User> user = userRepository.findByEmail(commentRequest.getUserEmail());
            if (!user.isPresent()){
                throw new EntityNotFoundException("User not found");
            } else {
                Optional<Post> post = postRepository.findById(commentRequest.getPostId());
                if (!post.isPresent()){
                    throw new EntityNotFoundException("Post not found");
                } else {
                    Comment comment = modelMapper.map(commentRequest, Comment.class);
                    comment.setUser(user.get());
                    comment.setPost(post.get());
                    commentRepository.save(comment);
                }
            }
        }

        @Override
        public void updateComment(CommentRequest commentRequest, Long id) throws EntityNotFoundException {
            Optional<Comment> comment = commentRepository.findById(id);
            if (!comment.isPresent()){
                throw new EntityNotFoundException("Comment not found");
            } else {
                Optional<User> user = userRepository.findByEmail(commentRequest.getUserEmail());;
                if (!user.isPresent()){
                    throw new EntityNotFoundException("User not found");
                } else {
                    Optional<Post> post = postRepository.findById(commentRequest.getPostId());
                    if (!post.isPresent()){
                        throw new EntityNotFoundException("Post not found");
                    } else {
                        Comment commentReceived = comment.get();
                        commentReceived.setContent(commentRequest.getContent());
                        commentReceived.setUser(user.get());
                        commentReceived.setPost(post.get());
                        commentRepository.save(commentReceived);
                    }
                }
            }
        }

        @Override
        public void deleteComment(Long id) throws EntityNotFoundException {
            Optional<Comment> comment = commentRepository.findById(id);
            if (!comment.isPresent()){
                throw new EntityNotFoundException("Comment not found");
            } else {
                Comment commentReceived = comment.get();
                commentRepository.delete(commentReceived);
            }
        }
}
