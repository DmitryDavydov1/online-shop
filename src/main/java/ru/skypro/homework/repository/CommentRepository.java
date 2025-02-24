package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.models.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT e FROM CommentEntity e WHERE e.ad.pk = :id")
    List<CommentEntity> findByAd(long id);
}
