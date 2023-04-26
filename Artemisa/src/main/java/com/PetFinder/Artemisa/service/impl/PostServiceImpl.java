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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<PostResponse> getAllPosts() throws EntityNotFoundException {
        List<Post> postList = postRepository.findAll();
        if (postList.isEmpty()){
            throw new EntityNotFoundException("Posts not found");
        } else {
            List<PostResponse> postResponseList = new ArrayList<>();
            for (Post post : postList) {
                PostResponse postResponse = modelMapper.map(post, PostResponse.class);
                postResponseList.add(postResponse);
            }
            return postResponseList;
        }
    }

    @Override
    public PostResponse getPostById(Long id) throws EntityNotFoundException{
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()){
            throw new EntityNotFoundException("Post not found");
        } else {
            Post postReceived = post.get();
            PostResponse postResponse = modelMapper.map(postReceived, PostResponse.class);
            return postResponse;
        }
    }

    @Override
    public void createPost(PostRequest postRequest) throws EntityNotFoundException{
        Optional<User> user = userRepository.findByEmail(postRequest.getUserEmail());
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        } else {
            Optional<Pet> pet = petRepository.findById(postRequest.getPetId());
            if (!pet.isPresent()){
                throw new EntityNotFoundException("Pet not found");
            } else {
                Post post = modelMapper.map(postRequest, Post.class);
                post.setUser(user.get());
                post.setPet(pet.get());
                post.setStatus(true);
                postRepository.save(post);
            }
        }
    }

    @Override
    public void updatePost(PostRequest postRequest, Long id) throws EntityNotFoundException {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()){
            throw new EntityNotFoundException("Post not found");
        } else {
            Optional<User> user = userRepository.findByEmail(postRequest.getUserEmail());
            if (!user.isPresent()){
                throw new EntityNotFoundException("User not found");
            } else {
                Optional<Pet> pet = petRepository.findById(postRequest.getPetId());
                if (!pet.isPresent()){
                    throw new EntityNotFoundException("Pet not found");
                } else {
                    Post postReceived = post.get();
                    postReceived.setTitle(postRequest.getTitle());
                    postReceived.setContent(postRequest.getContent());
                    postReceived.setImage(postRequest.getImage());
                    postReceived.setPostType(postRequest.getPostType());
                    postReceived.setUser(user.get());
                    postReceived.setPet(pet.get());
                    postRepository.save(postReceived);
                }
            }
        }
    }

    @Override
    public void deletePost(Long id) throws EntityNotFoundException {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()){
            throw new EntityNotFoundException("Post not found");
        } else {
            postRepository.deleteById(id);
        }
    }

    @Override
    public List<PostResponse> getPostsByUserId(Long id) throws EntityNotFoundException{
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new EntityNotFoundException("User not found");
        } else {
            List<Post> postList = postRepository.findByUser(user.get());
            if (postList.isEmpty()){
                throw new EntityNotFoundException("Posts not found");
            } else {
                List<PostResponse> postResponseList = new ArrayList<>();
                for (Post post : postList) {
                    PostResponse postResponse = modelMapper.map(post, PostResponse.class);
                    postResponseList.add(postResponse);
                }
                return postResponseList;
            }
        }
    }

    @Override
    public PostResponse getPostsByPetId(Long id) throws EntityNotFoundException{
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            throw new EntityNotFoundException("Pet not found");
        } else {
            Post post = postRepository.findByPet(pet.get());
            if (post == null){
                throw new EntityNotFoundException("Post not found");
            } else {
                PostResponse postResponse = modelMapper.map(post, PostResponse.class);
                return postResponse;
            }
        }
    }
}
