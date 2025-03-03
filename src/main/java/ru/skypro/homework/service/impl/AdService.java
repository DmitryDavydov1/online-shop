package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;



@Service
public class AdService {

    private final AdRepository adRepository;

    private final AdsMapper adsMapper;
    @Value("${avatars.ads.dir}")
    private String adsDir;

    private final FileService fileService;
    private final UserRepository userRepository;

    public AdService(AdRepository adRepository, AdsMapper adsMapper, FileService fileService, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.adsMapper = adsMapper;
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

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

    public Ad addAd(CreateOrUpdateAd ad, MultipartFile image) throws IOException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(name).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден: " + name));
        String path = ad + "." + getExtensions((Objects.requireNonNull(image.getOriginalFilename())));
        String filePath = fileService.uploadFile(adsDir, path, image);
        AdEntity adEntity = adsMapper.toAdEntity(ad, user, filePath);
        adRepository.save(adEntity);
        return adsMapper.toAd(adEntity);
    }


    public boolean getIsAdOwner(Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(name).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден: " + name));
        AdEntity adEntity = adRepository.findById(id).get();
        return user.getId().equals(adEntity.getAuthor().getId());

    }
}
