package com.blog.blog.controller;

import com.blog.blog.Entities.Comment;
import com.blog.blog.Payload.CommentDto;
import com.blog.blog.Service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("postId") long postId,
            @RequestBody CommentDto commentDto
    ) {
        CommentDto dto = commentService.saveComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsBypostId(@PathVariable("postId") long postId) {
        return commentService.getCommentBypostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable("postId") long postId, @PathVariable("commentId") long CommentId) {
        CommentDto dto = commentService.getCommentById(postId, CommentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
           @PathVariable ("postId") long postId,
           @PathVariable("id") long id,
           @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateComment(postId, id ,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable ("postId") long postId,
            @PathVariable("id") long id
    ){
        commentService.deleteComment(postId,id);
        return new ResponseEntity<>("comment is deleted", HttpStatus.OK);
    }
}


