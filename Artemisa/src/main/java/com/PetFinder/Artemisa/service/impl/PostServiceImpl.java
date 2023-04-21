package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.dto.PostDto;
import com.PetFinder.Artemisa.model.Post;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.repository.PetRepository;
import com.PetFinder.Artemisa.repository.PostRepository;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public PostDto getPublicationById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Publication not found"));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public void createPublication(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        postRepository.save(post);
    }

    @Override
    public void updatePublication(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        postRepository.save(post);
    }

    @Override
    public void deletePublication(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Publication not found"));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPublicationsByUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        List<Post> posts = (List<Post>) user.getPosts();
        List<PostDto> postDtos = modelMapper.map(posts, List.class);
        return postDtos;
    }
}
