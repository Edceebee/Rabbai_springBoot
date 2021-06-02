package com.blogapp.service.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.data.repository.PostRepository;
import com.blogapp.service.cloud.CloudStorageService;
import com.blogapp.web.controllers.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PostServiceImpl implements  PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    CloudStorageService cloudStorageService;

    @Override
    public Post savePost(PostDto postDto)  throws PostObjectIsNullException{

        if(postDto == null){
            throw new PostObjectIsNullException("Post cannot be null");
        }

        Post post = new Post();

        if(postDto.getImageFile() != null){
//            Map<Object, Object> params = new HashMap<>();
//            params.put("public_id", "blogapp/" + postDto.getImageFile().getName());
//            params.put("overwrite", true);
//            log.info("Image Parameters --> {}", params);

            try {
                Map<?, ?> uploadResult = cloudStorageService.uploadImage(postDto.getImageFile(),
                        ObjectUtils.asMap(
                                "public_id", "blog_app/"+ postDto.getImageFile().getName(), "overwrite", true
                        ));

                post.setCoverImage(String.valueOf(uploadResult.get("url")));
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        post.setTitle(postDto.getTitle());
        post.setContent(post.getContent());

        log.info("Post object before saving --> {}", post);

//        ModelMapper modelMapper = new  ModelMapper();
//        modelMapper.map(postDto, post);
//
//        log.info("Post object after mapping --> {}", post);

        return postRepository.save(post);
    }

    @Override
    public List<Post> findAllPost() {
        return null;
    }

    @Override
    public Post updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public Post findById(Integer id) {
        return null;
    }

    @Override
    public void deletePostById(Integer id) {

    }

    @Override
    public Post addCommentToPost(Integer id, Comment comment) {
        return null;
    }
}
