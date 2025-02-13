package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class CommentController {

    @GetMapping(path = "/ads/{id}/comments")
    public ResponseEntity<Comments> commentsById(@PathVariable int id) {
        return null;
    }


    @PostMapping(path = "/ads/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable int id, @RequestBody CreateOrUpdateComment comment) {
        return null;
    }


    @DeleteMapping(path = "/ads/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int adId, @PathVariable int commentId) {
        return null;
    }

    @PatchMapping(path = "/ads/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable int adId, @PathVariable int commentId, @RequestBody CreateOrUpdateComment comment) {
        return null;
    }

}
