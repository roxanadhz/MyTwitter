package com.trilogyed.stwitter.controller;

import com.netflix.discovery.converters.Auto;
import com.trilogyed.stwitter.model.SwitterViewModel;
import com.trilogyed.stwitter.service.ServiceLayer;
import com.trilogyed.stwitter.util.feign.PostClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class StwitterController {

    @Autowired
    private ServiceLayer service;


    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public SwitterViewModel createPost(@RequestBody @Valid SwitterViewModel switterViewModel){
    return service.CreatePost(switterViewModel);
    }

    @GetMapping("/posts/{id}")
    public SwitterViewModel getPost(@PathVariable int id){
        return service.getPost(id);
    }

    @GetMapping("/posts/user/{poster-name}")
    public List<SwitterViewModel> getPostByPosterName(@PathVariable String posterName){
        return service.getPostByPosterName(posterName);
    }
}
