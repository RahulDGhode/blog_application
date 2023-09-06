package com.blog.blog.controller;

import com.blog.blog.Payload.PostDto;
import com.blog.blog.Payload.PostResponce;
import com.blog.blog.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity <?> createPost (@Valid @RequestBody PostDto postDto, BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity <>(dto, HttpStatus.CREATED);

    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public PostResponce getAllPosts(
            @RequestParam (value ="pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam( value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam( value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam( value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PostResponce postResponce = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return postResponce;
    }
    //http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updateById (
                            @RequestBody PostDto postDto,
                            @PathVariable("id") long id
    ){
        PostDto dto = postService.updateById(postDto,id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById (@PathVariable ("id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);

    }
}
