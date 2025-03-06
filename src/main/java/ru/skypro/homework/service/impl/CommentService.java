package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.models.CommentEntity;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final AdRepository adRepository;

    public CommentService(AdRepository adRepository, CommentRepository commentRepository, CommentMapper commentMapper, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
    }


    public Comments getCommentByAd(long id) {
        List<CommentEntity> comments = commentRepository.findByAd(id);

        List<Comment> commentList = comments.stream().map(commentEntity ->
                        commentMapper.toComment(commentEntity)).
                collect(Collectors.toList());

        return commentMapper.toComments(commentList);
    }


    public Comment addCommentToAd(long id, CreateOrUpdateComment createOrUpdateComment) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
        UserEntity userEntity = userRepository.findByEmail(name).orElseThrow();
        AdEntity adEntity = adRepository.findById(id).get();

        CommentEntity commentEntity = commentMapper.toCommentEntity(createOrUpdateComment, userEntity, adEntity);
        commentRepository.save(commentEntity);

        return commentMapper.toComment(commentEntity);
    }

    public Comment updateComment(long id, long commentId, CreateOrUpdateComment createOrUpdateComment) {
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        commentEntity.setText(createOrUpdateComment.getText());
        commentRepository.save(commentEntity);
        return commentMapper.toComment(commentEntity);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }


    public boolean getCoemment(long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(name).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден: " + name));
        CommentEntity commentEntity = commentRepository.findById(id).get();
        return user.getId().equals(commentEntity.getUser().getId());
    }
}
