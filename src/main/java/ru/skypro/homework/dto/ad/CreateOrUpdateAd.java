package ru.skypro.homework.dto.ad;

import lombok.Data;

@Data
public class CreateOrUpdateAd {
    private String title;
    private String price;
    private String description;
}
