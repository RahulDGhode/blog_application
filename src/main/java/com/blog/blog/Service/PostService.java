package com.blog.blog.Service;

import com.blog.blog.Entities.Post;
import com.blog.blog.Payload.PostDto;
import com.blog.blog.Payload.PostResponce;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    public PostResponce getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);

    PostDto updateById(PostDto postDto, long id);

    void deleteById(long id);

}
