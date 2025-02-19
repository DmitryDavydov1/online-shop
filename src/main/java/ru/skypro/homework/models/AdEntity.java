package ru.skypro.homework.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class AdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pk;
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    private long price;

    private String description;

    private String image;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;



}
