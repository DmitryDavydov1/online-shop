package ru.skypro.homework.dto.ad;

import lombok.Data;

@Data
public class Ad {
    private int author;
    private String image;
    private String title;
    private int price;
    private int pk;
}
