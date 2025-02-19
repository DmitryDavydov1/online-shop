package ru.skypro.homework.dto.ad;

import lombok.Data;

@Data
public class Ad {
    private long pk;
    private long author;
    private String image;
    private String title;
    private long price;
}
