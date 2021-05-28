package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
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
    void findAllPostInTheDbTest(){

        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);

        log.info("Post Author --{}", existingPosts.get(0).getAuthor());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void deletePostByIdTest(){
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        log.info("Post fetched from the database --> {}", savedPost);

        //delete post
        postRepository.deleteById(savedPost.getId());

        Post deletedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(deletedPost).isNull();

    }


    @Test
    void updatedSavePostInTitle(){
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("Title post 1");

        //updated post title
        savedPost.setTitle("New title");
        postRepository.save(savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getTitle()).isEqualTo("New title");

    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updateAuthor(){
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
//        assertThat(savedPost.getAuthor()).isNotNull();

        Author author = new Author();
        author.setLastName("Brown");
        author.setFirstName("Blue");
        author.setPhoneNumber("09024t64817");
        author.setEmail("Brown@mail.com");
        author.setProfession("Musician");

        savedPost.setAuthor(author);
        postRepository.save(savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getAuthor()).isNotNull();
        assertThat(updatedPost.getAuthor().getLastName()).isEqualTo("Brown");

        log.info("Updated post --> {}", updatedPost);



    }

    @Test
    @Transactional
    @Rollback(value = false)
    void addCommentToExistingComment(){
        //fetch the post from db
        Post post  = postRepository.findById(43).orElse(null);
        assertThat(post).isNotNull();
        assertThat(post.getComments()).hasSize(0);

        // create comment
        Comment comment1 = new Comment("He's the goat", "Billy Goat" );
        Comment comment2 = new Comment("I too like bread", "Peter");

        //map the post to the comment
        post.addComment(comment1, comment2);

        //save the post
        postRepository.save(post);

        log.debug("Post --> {}", post);

        Post commentedPost = postRepository.findById(post.getId()).orElse(null);

        log.debug("Commented Post --> {}", commentedPost);

        assertThat(commentedPost).isNotNull();

//        assertThat(comment1.getCommentatorName()).isEqualTo("Billy Goat");
//        assertThat(comment2.getCommentatorName()).isEqualTo("Peter");

        assertThat(commentedPost.getComments()).hasSize(2);
        log.info("Commented Post --> {}", commentedPost);

    }
}