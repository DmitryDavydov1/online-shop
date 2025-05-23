package ru.skypro.homework.dto.ad;

import lombok.Data;

@Data
public class ExtendedAd {
    private long pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private long price;
    private String title;

}
