package com.blog.blog.Service.Impl;

import com.blog.blog.Entities.Post;
import com.blog.blog.Exceptions.ResourceNotFoundException;
import com.blog.blog.Payload.PostDto;
import com.blog.blog.Payload.PostResponce;
import com.blog.blog.Repositories.PostRepository;
import com.blog.blog.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);

        Post savedPost = postRepository.save(post);

       PostDto dto=  mapToDto(savedPost);

        return dto;
    }
    @Override
    public PostResponce getAllPosts(int pageNo,int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> content = postRepository.findAll(pageable);
        List <Post> posts =content.getContent();

        List <PostDto> Dtos =  posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());

        PostResponce postResponce = new PostResponce();
        postResponce.setContent(Dtos);
        postResponce.setPageNo(content.getNumber());
        postResponce.setPageSize(content.getSize());
        postResponce.setTotalElement(content.getTotalElements());
        postResponce.setTotalPages(content.getTotalPages());
        return postResponce;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("Post not found with id :"+id)
            );
         PostDto postDto= mapToDto(post);
        return postDto;
        }

    @Override
    public PostDto updateById(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with id :"+id)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);

    }

    @Override
    public void deleteById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with id :"+id)
        );
        postRepository.deleteById(id);
    }

    Post mapToEntity (PostDto postDto){
       Post post = mapper.map(postDto, Post.class);
//            Post post = new Post() ;
//            post.setTitle(postDto.getTitle());
//            post.setDescription(postDto.getDescription());
//            post.setContent(postDto.getContent());
        return post;

            }
            PostDto mapToDto (Post post){
                PostDto dto = mapper.map(post, PostDto.class);
//                PostDto dto = new PostDto();
//                dto.setId(post.getId());
//                dto.setTitle(post.getTitle());
//                dto.setContent(post.getContent());
//                dto.setDescription(post.getDescription());
                return dto;
    }
}
