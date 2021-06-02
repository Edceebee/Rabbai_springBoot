package com.blogapp.service.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class CloudinaryCloudStorageServiceImplTest {


    @Autowired @Qualifier("cloudinary")
    CloudStorageService cloudStorageServiceImpl;


    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadImage() {

        //define a file
        File file = new File("C:\\Users\\USCHIP\\demoblogapp\\src\\main\\resources\\static\\asset\\images\\blog-image1.jpg");

        assertThat(file.exists()).isTrue();
        Map<Object, Object> params = new HashMap<>();
        params.put("file", "/blogapp");

        try {
            cloudStorageServiceImpl.uploadImage(file, params);
        } catch(IOException e){
            log.info("Error occurred --> {}", e.getMessage());
        }

    }


    @Test
    void uploadMultipartImage() {

        //define a file
        File file = new File("C:\\Users\\USCHIP\\demoblogapp\\src\\main\\resources\\static\\asset\\images\\blog-image1.jpg");

        assertThat(file.exists()).isTrue();
        Map<Object, Object> params = new HashMap<>();
        params.put("file", "/blogapp");

        try {
            cloudStorageServiceImpl.uploadImage(file, params);
        } catch(IOException e){
            log.info("Error occurred --> {}", e.getMessage());
        }

    }
}