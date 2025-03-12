package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.models.CommentEntity;
import ru.skypro.homework.service.impl.CommentService;

import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping(path = "/ads/{id}/comments")
    public ResponseEntity<Comments> commentsById(@PathVariable int id) {
        return ResponseEntity.ok().body(commentService.getCommentByAd(id));
    }


    @PostMapping(path = "/ads/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable int id, @RequestBody CreateOrUpdateComment comment) {
        return ResponseEntity.ok().body(commentService.addCommentToAd(id, comment));
    }

    @PreAuthorize("@commentService.getComment(#commentId)")
    @DeleteMapping(path = "/ads/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int adId, @PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@commentService.getComment(#commentId)")
    @PatchMapping(path = "/ads/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable int adId, @PathVariable int commentId, @RequestBody CreateOrUpdateComment comment) {
        return ResponseEntity.ok().body(commentService.updateComment(adId, commentId, comment));
    }


}
