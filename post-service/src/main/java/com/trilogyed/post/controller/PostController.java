package com.trilogyed.post.controller;

import com.trilogyed.post.dao.PostRepository;
import com.trilogyed.post.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RefreshScope
@CacheConfig(cacheNames={"posts"})
public class PostController {
    @Autowired
    private PostRepository postRepo;

    @CachePut(key = "#post.getId()")
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public Post createPost(@RequestBody Post post) {
        postRepo.save(post);
        return post;
    }

    @Cacheable
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable int id) {
    Optional<Post> post = postRepo.findById(id);
        return post.orElse(null);
    }

    @CacheEvict
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    public void updatePost(@RequestBody Post post, @PathVariable int id) {

    }

    @CacheEvict
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable int id) {
        postRepo.deleteById(id);
    }


    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }


    @RequestMapping(value = "/posts/user/{poster_name}" , method = RequestMethod.GET)
    public List<Post> getPostByPosterName(@PathVariable String posterName){
        return postRepo.findByPosterName(posterName);
    }
}
