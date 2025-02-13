package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;

import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AdController {


    @GetMapping(path = "/ads")
    public ResponseEntity<Ads> getAllAds() {
        return null;
    }

    @PostMapping(path = "/ads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ads> addAd(@RequestBody CreateOrUpdateAd ad, @RequestParam MultipartFile file) {
        return null;
    }


    @GetMapping(path = "/ads/{id}")
    public ResponseEntity<ExtendedAd> getAdById(@PathVariable int id) {
        return null;
    }

    @DeleteMapping(path = "/ads/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable int id) {
        return null;
    }

    @PatchMapping(path = "/ads/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable int id, @RequestBody CreateOrUpdateAd ad) {
        return null;
    }


    @GetMapping(path = "/ads/me")
    public ResponseEntity<Ads> getAd() {
        return null;
    }

    @PatchMapping(path = "ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> updateAdImage(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        return null;
    }
}
