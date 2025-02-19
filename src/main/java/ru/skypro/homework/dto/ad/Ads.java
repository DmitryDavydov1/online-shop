package ru.skypro.homework.dto.ad;

import lombok.Data;

import java.util.List;
@Data
public class Ads {
    private long count;
    private List<Ad> results;
}
