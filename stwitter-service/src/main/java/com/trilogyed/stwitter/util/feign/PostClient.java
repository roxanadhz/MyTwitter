package com.trilogyed.stwitter.util.feign;

import com.trilogyed.stwitter.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "post-service")
public interface PostClient {

    @PostMapping("/posts")
    Post createPost(@RequestBody Post post);

    @GetMapping(value = "/posts/{id}")
    Optional<Post> getPost(@PathVariable int id);

    @GetMapping("/posts/user/{poster_name}")
    List<Post> getPostByPosterName(@PathVariable String posterName);
}
