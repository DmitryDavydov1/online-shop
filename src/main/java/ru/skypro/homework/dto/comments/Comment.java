package ru.skypro.homework.dto.comments;

import lombok.Data;

@Data
public class Comment {
    private String author;
    private String authorImage;
    private String authorFirstName;
    private int createdAt;
    private int pk;
    private String text;
}
