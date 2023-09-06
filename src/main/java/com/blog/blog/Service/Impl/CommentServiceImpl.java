package com.blog.blog.Service.Impl;

import com.blog.blog.Entities.Comment;
import com.blog.blog.Entities.Post;
import com.blog.blog.Exceptions.BlogAPIException;
import com.blog.blog.Exceptions.ResourceNotFoundException;
import com.blog.blog.Payload.CommentDto;
import com.blog.blog.Repositories.CommentRepository;
import com.blog.blog.Repositories.PostRepository;
import com.blog.blog.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper= mapper;
    }

    @Override
    public CommentDto saveComment(Long postId, CommentDto commentDto){
       Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

       return mapToDto(newComment);

    }

    @Override
    public List<CommentDto> getCommentBypostId(long postId) {
        List<Comment> comments = commentRepository.findBypostId(postId);
        return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment not found with id " + commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("comment does not belong to this post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post =postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post not found with this :"+postId));
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment not found with this :" +id));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("comment does not belongs to this post");
        }
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post =postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post not found with this :"+postId));
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment not found with this :" +id));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("comment does not belongs to this post");
        }

        commentRepository.deleteById(id);
    }

    Comment mapToEntity (CommentDto commentDto ){
        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setBody(commentDto.getBody());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());

        return comment;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = mapper.map(comment, CommentDto.class);
//        CommentDto dto = new CommentDto();
//        dto.setId(comment.getId());
//        dto.setBody(comment.getBody());
//        dto.setEmail(comment.getEmail());
//        dto.setName(comment.getName());

        return dto;
    }

}