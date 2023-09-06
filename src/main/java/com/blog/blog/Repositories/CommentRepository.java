package com.blog.blog.Repositories;

import com.blog.blog.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {

    List<Comment> findBypostId(long postId);

}
