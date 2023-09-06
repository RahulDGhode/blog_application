package com.blog.blog.Payload;

import lombok.Data;

import java.util.List;

@Data
public class PostResponce {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean isLast;

}
