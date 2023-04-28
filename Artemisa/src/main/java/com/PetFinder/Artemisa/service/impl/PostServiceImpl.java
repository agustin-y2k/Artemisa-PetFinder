package com.PetFinder.Artemisa.service.impl;

import com.PetFinder.Artemisa.exception.EntityNotFoundException;
import com.PetFinder.Artemisa.model.Pet;
import com.PetFinder.Artemisa.model.Post;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.model.payloads.PostRequest;
import com.PetFinder.Artemisa.model.payloads.PostResponse;
import com.PetFinder.Artemisa.repository.PetRepository;
import com.PetFinder.Artemisa.repository.PostRepository;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PetRepository petRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PetRepository petRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    @Override
    public List<PostResponse> getAllPosts() throws EntityNotFoundException {

        List<Post> postList = postRepository.findAll();
        if (postList.isEmpty()){
            throw new EntityNotFoundException("Posts not found");
        }

        List<PostResponse> postResponseList = new ArrayList<>();
        for (Post post : postList) {
            PostResponse postResponse = modelMapper.map(post, PostResponse.class);
            postResponseList.add(postResponse);
        }
        return postResponseList;
    }

    @Override
    public PostResponse getPostById(Long id) throws EntityNotFoundException{

        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()){
            throw new EntityNotFoundException("Post not found");
        }

        Post postReceived = post.get();
        PostResponse postResponse = modelMapper.map(postReceived, PostResponse.class);
        return postResponse;
    }

    @Override
    public void createPost(PostRequest postRequest) throws EntityNotFoundException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUser = userDetails.getUsername();

        if (!userRepository.findByEmail(emailUser).isPresent()){
            throw new EntityNotFoundException("User not found");
        }

        Optional<Pet> pet = petRepository.findById(postRequest.getPetId());
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        }

        Post post = modelMapper.map(postRequest, Post.class);
        post.setUser(userRepository.findByEmail(emailUser).get());
        post.setPet(pet.get());
        post.setStatus(true);
        postRepository.save(post);
    }

    @Override
    public void updatePost(PostRequest postRequest, Long id) throws EntityNotFoundException {

        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()){
            throw new EntityNotFoundException("Post not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUser = userDetails.getUsername();

        if (post.get().getUser().getEmail().equals(emailUser) || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){

            Optional<Pet> pet = petRepository.findById(postRequest.getPetId());
            if (!pet.isPresent()){
                throw new EntityNotFoundException("Pet not found");
            }

            Post postReceived = post.get();
            postReceived.setTitle(postRequest.getTitle());
            postReceived.setContent(postRequest.getContent());
            postReceived.setImage(postRequest.getImage());
            postReceived.setPostType(postRequest.getPostType());
            postReceived.setPet(pet.get());
            postRepository.save(postReceived);
            }
        else {
            throw new EntityNotFoundException("You are not authorized to update this post");
        }
    }

    @Override
    public void deletePost(Long id) throws EntityNotFoundException {

        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()){
            throw new EntityNotFoundException("Post not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUser = userDetails.getUsername();

        if (post.get().getUser().getEmail().equals(emailUser) || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
            postRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("You are not authorized to delete this post");
        }
    }

    @Override
    public List<PostResponse> getPostsByUserEmail(String email) throws EntityNotFoundException{
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        }

        List<Post> postList = postRepository.findByUser(user.get());
        if (postList.isEmpty()){
            throw new EntityNotFoundException("Posts not found");
        }

        List<PostResponse> postResponseList = new ArrayList<>();
        for (Post post : postList) {
            PostResponse postResponse = modelMapper.map(post, PostResponse.class);
            postResponseList.add(postResponse);
        }
        return postResponseList;
    }

    @Override
    public PostResponse getPostsByPetId(Long id) throws EntityNotFoundException{
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        }

        Post post = postRepository.findByPet(pet.get());
        if (post == null){
            throw new EntityNotFoundException("Post not found");
        }

        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        return postResponse;
    }
}
