package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.repository.AdRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdsMapper adsMapper;
    @Value("${avatars.ads.dir}")
    private String adsDir;

    @Autowired
    private FileService fileService;

    public Ads getAllAds() {
        List<AdEntity> allAds = adRepository.findAll();
        List<Ad> adsList = allAds.stream().map(adEntity -> adsMapper.toAd(adEntity))
                .collect(Collectors.toList());
        return adsMapper.toAds(adsList);
    }

    public ExtendedAd getAdById(long id) {
        AdEntity user = adRepository.findById(id).orElseThrow(() -> new RuntimeException("Такого объявления нет"));
        return adsMapper.toExtendedAd(user);
    }

    public boolean deleteAdById(long id) {
        boolean exists = adRepository.existsById(id);
        if (exists) {
            adRepository.deleteById(id);
            return true;
        }
        return false;

    }

    public Ads getAdsAuthorizedUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        List<AdEntity> allAds = adRepository.findAll();
        List<Ad> listAd = allAds.stream().filter(ad -> ad.getAuthor().getEmail().equals(name)).
                map(adEntity -> adsMapper.toAd(adEntity)).collect(Collectors.toList());
        return adsMapper.toAds(listAd);
    }

    public Ad updateAd(long idAd, CreateOrUpdateAd update) {
        AdEntity ad = adRepository.findById(idAd).get();


        ad.setPrice(update.getPrice());
        ad.setDescription(update.getDescription());
        ad.setText(update.getTitle());

        Ad newAd = adsMapper.toAd(ad);

        adRepository.save(ad);
        return newAd;
    }

    public void updateImage(long idAd, MultipartFile image) throws IOException {
        AdEntity ad = adRepository.findById(idAd).get();
        String path = ad + "." + getExtensions((Objects.requireNonNull(image.getOriginalFilename())));
        String filePath = fileService.uploadFile(adsDir, path, image);
        ad.setImage(filePath);
        adRepository.save(ad);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
