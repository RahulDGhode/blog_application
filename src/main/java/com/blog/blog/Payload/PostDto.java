package com.blog.blog.Payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 3, message = "title should be atleast 3 characters")
    private String title;

    @NotEmpty
    @Size(min= 10, message = "description should contain atleast 10 characters")
    private String description;

    @NotEmpty
    @Size(min = 10, message = "content should have 10 characters minimum" )
    private String content;
}
