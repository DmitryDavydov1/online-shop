package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.ad.Ad;
import ru.skypro.homework.dto.ad.Ads;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.models.UserEntity;

import java.util.List;

@Component
public class AdsMapper {
    public AdEntity toAdEntity(CreateOrUpdateAd createOrUpdateAd, UserEntity userEntity, String image) {
        AdEntity adEntity = new AdEntity();
        adEntity.setAuthor(userEntity);
        adEntity.setText(createOrUpdateAd.getTitle());
        adEntity.setPrice(createOrUpdateAd.getPrice());
        adEntity.setImage(image);
        adEntity.setDescription(createOrUpdateAd.getDescription());
        return adEntity;
    }

    public Ad toAd(AdEntity adEntity) {
        Ad ad = new Ad();

        ad.setTitle(adEntity.getText());
        ad.setPrice(adEntity.getPrice());
        ad.setImage(adEntity.getImage());
        ad.setPk(adEntity.getPk());
        ad.setAuthor(adEntity.getAuthor().getId());

        return ad;
    }

    public ExtendedAd toExtendedAd(AdEntity adEntity) {
        ExtendedAd extendedAd = new ExtendedAd();

        extendedAd.setTitle(adEntity.getText());
        extendedAd.setPrice(adEntity.getPrice());
        extendedAd.setImage(adEntity.getImage());
        extendedAd.setAuthorFirstName(adEntity.getAuthor().getFirstName());
        extendedAd.setAuthorLastName(adEntity.getAuthor().getLastName());
        extendedAd.setPhone(adEntity.getAuthor().getPhone());
        extendedAd.setEmail(adEntity.getAuthor().getEmail());
        extendedAd.setDescription(adEntity.getText());
        extendedAd.setTitle(adEntity.getText());
        extendedAd.setDescription(adEntity.getDescription());
        extendedAd.setPk(adEntity.getPk());

        return extendedAd;
    }


    public Ads toAds(List<Ad> adEntities) {
        Ads ads = new Ads();
        ads.setCount(adEntities.size());
        ads.setResults(adEntities);
        return ads;
    }
}
