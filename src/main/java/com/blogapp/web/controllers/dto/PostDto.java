package com.blogapp.web.controllers.dto;

import org.springframework.web.multipart.MultipartFile;

public class PostDto {
    private String title;

    private String content;

    private MultipartFile imageFile;
}
