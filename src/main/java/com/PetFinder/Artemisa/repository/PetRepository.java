package com.PetFinder.Artemisa.repository;

import com.PetFinder.Artemisa.model.Pet;
import com.PetFinder.Artemisa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwner(User user);
}
