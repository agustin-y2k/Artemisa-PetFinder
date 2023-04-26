package com.PetFinder.Artemisa.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type")
    private PetType petType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
