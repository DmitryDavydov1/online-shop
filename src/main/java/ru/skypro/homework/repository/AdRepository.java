package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.AdEntity;

public interface AdRepository extends JpaRepository<AdEntity, Long> {
}
