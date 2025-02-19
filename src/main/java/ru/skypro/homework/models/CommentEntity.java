package ru.skypro.homework.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String text;

    private long createdAt;

    @ManyToOne
    @JoinColumn(name  = "ad_id")
    private AdEntity ad;

}
