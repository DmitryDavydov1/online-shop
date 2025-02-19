package ru.skypro.homework.dto.comments;

import lombok.Data;

@Data
public class Comment {
    private long pk;
    private long author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private String text;
}
