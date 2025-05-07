package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.service.impl.AdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AdController {

    @Autowired
    private AdService adService;
    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping(path = "/ads")
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok().body(adService.getAllAds());
    }

    @PostMapping(value = "/ads", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ad> createAd(
            @RequestPart(value = "properties") String adJson,
            @RequestPart(value = "image") MultipartFile image) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        CreateOrUpdateAd ad = objectMapper.readValue(adJson, CreateOrUpdateAd.class);

        return ResponseEntity.ok().body(adService.addAd(ad, image));
    }


    @GetMapping(path = "/ads/{id}")
    public ResponseEntity<ExtendedAd> getAdById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(adService.getAdById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("@adService.getIsAdOwner(#id)")
    @DeleteMapping(path = "/ads/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable int id) {
        if (adService.deleteAdById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PreAuthorize("@adService.getIsAdOwner(#id)")
    @PatchMapping(path = "/ads/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable long id, @RequestBody CreateOrUpdateAd ad) {
        return ResponseEntity.ok().body(adService.updateAd(id, ad));
    }


    @GetMapping(path = "/ads/me")
    public ResponseEntity<Ads> getAd() {
        return ResponseEntity.ok().body(adService.getAdsAuthorizedUser());
    }

    @PreAuthorize("@adService.getIsAdOwner(#id)")
    @PatchMapping(path = "ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> updateAdImage(@PathVariable int id, @RequestParam("file") MultipartFile file) throws IOException {
        adService.updateImage(id, file);
        List<String> list = new ArrayList<>(List.of("string"));
        return ResponseEntity.ok(list);
    }
}
