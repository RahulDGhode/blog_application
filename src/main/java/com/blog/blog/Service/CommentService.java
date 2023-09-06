package com.blog.blog.Service;

import com.blog.blog.Entities.Comment;
import com.blog.blog.Exceptions.BlogAPIException;
import com.blog.blog.Payload.CommentDto;

import java.util.List;

public interface CommentService {
  CommentDto saveComment(Long postId, CommentDto commentDto);

  List<CommentDto> getCommentBypostId(long postId);

  CommentDto getCommentById(long postId, long commentId);

  CommentDto updateComment(long postId, long id, CommentDto commentDto);

  void deleteComment(long postId, long id);
}
