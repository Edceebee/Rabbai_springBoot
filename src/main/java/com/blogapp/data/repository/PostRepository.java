package com.blogapp.data.repository;

import com.blogapp.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findByTitle(String title);

    @Query("select p from blog_post p where p.title = :title")
    Post findByPostTitle(@Param("title") String title);



}
