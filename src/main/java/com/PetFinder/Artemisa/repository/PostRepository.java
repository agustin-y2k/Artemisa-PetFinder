package com.PetFinder.Artemisa.repository;

import com.PetFinder.Artemisa.model.Pet;
import com.PetFinder.Artemisa.model.Post;
import com.PetFinder.Artemisa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    Post findByPet(Pet pet);
}
