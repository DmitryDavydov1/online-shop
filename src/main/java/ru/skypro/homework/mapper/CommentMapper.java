package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.models.CommentEntity;
import ru.skypro.homework.models.UserEntity;

import java.util.List;

@Component
public class CommentMapper {
    public Comment toComment(CommentEntity commentEntity) {
        Comment comment = new Comment();

        comment.setPk(commentEntity.getUser().getId());
        comment.setAuthorImage(commentEntity.getUser().getImage());
        comment.setAuthorFirstName(commentEntity.getUser().getFirstName());
        comment.setCreatedAt(commentEntity.getCreatedAt());
        comment.setPk(commentEntity.getPk());
        comment.setText(commentEntity.getText());

        return comment;
    }

    public CommentEntity toCommentEntity(CreateOrUpdateComment createOrUpdateComment, UserEntity userEntity, AdEntity adEntity) {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setText(createOrUpdateComment.getText());
        commentEntity.setAd(adEntity);
        commentEntity.setUser(userEntity);
        commentEntity.setCreatedAt(System.currentTimeMillis());


        return commentEntity;
    }


    public Comments toComments(List<Comment> commentEntities) {
        Comments comments = new Comments();
        comments.setCount(commentEntities.size());
        comments.setResults(commentEntities);
        return comments;
    }
}
