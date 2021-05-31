package com.blogapp.web.controllers.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
@Data
public class PostDto {
    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Message cannot be null")
    private String content;

    private MultipartFile imageFile;
}
