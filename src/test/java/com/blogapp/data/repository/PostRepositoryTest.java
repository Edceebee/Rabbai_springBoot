package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})

class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void savePostToDBTest(){

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Hola mi nombre es el niño estrella");

        log.info("Created a blog --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
    }


    @Test
    void throwExceptionWhenSavingPostWithDuplicateTitle(){

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Hola mi nombre es el niño estrella");
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
        log.info("Created a blog --> {}", blogPost);

        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech");
        blogPost2.setContent("Hola mi nombre es el niño estrella");
        log.info("Created a blog --> {}", blogPost2);
        assertThrows(DataIntegrityViolationException.class, () -> postRepository.save(blogPost2));

    }


    @Test
    void whenPostIsSaved_thenTheAuthor(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Hola mi nombre es el niño estrella");

        log.info("Created a blog --> {}", blogPost);

        Author author = new Author();
        author.setFirstName("Nonso");
        author.setLastName("Tony");
        author.setEmail("Nonso@gmail.com");
        author.setPhoneNumber("09079969789");

        // map relations
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("Blog post after saving --> {}", blogPost);

    }

    @Test
    void findAPostInTheDbTest(){

        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);
    }
}