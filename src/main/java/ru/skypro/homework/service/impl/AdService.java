package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdsMapper adsMapper;

    public Ads getAllAds() {
        List<AdEntity> allAds = adRepository.findAll();
        List<Ad> adsList = allAds.stream().map(adEntity -> adsMapper.toAd(adEntity))
                .collect(Collectors.toList());
        return adsMapper.toAds(adsList);
    }

    public ExtendedAd getAdById(long id) {
        AdEntity user = adRepository.findById(id).get();
        return adsMapper.toExtendedAd(user);
    }

    public void deleteAdById(long id) {
        adRepository.deleteById(id);
        adRepository.existsById(id);
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

}
