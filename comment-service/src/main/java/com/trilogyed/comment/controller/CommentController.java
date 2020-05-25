package com.trilogyed.comment.controller;

import com.trilogyed.comment.dao.CommentRepository;
import com.trilogyed.comment.model.Comment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CacheConfig(cacheNames={"comments"})
public class CommentController {


    @Autowired
    private CommentRepository commentRepo;

    @CachePut(key = "#commentToReturn.getId()")
    @PostMapping("/comments")
    public Comment createComment(@RequestBody Comment comment){
        Comment commentToReturn = commentRepo.save(comment);
        return commentToReturn;
    }

    @Cacheable
    @GetMapping("/comments/{id}")
    public Comment getComment(@PathVariable int id){
        return commentRepo.getOne(id);
    }

    @GetMapping("/comments")
     public List<Comment> getAllComments(){
        return commentRepo.findAll();
    }


    @CacheEvict
    @PutMapping("/comments/{id}")
    public void updateComment(@RequestBody Comment comment, @PathVariable int id){
    comment.setCommentId(id);
    commentRepo.save(comment);
    }

    @CacheEvict
    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable int id){
    commentRepo.deleteById(id);
    }

    }