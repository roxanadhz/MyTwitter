package com.company.commentqueueservice.controller;


import com.company.commentqueueservice.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "comment-service")
public interface CommentServiceClient {

    @PostMapping("/comment")
    void createComment(@RequestBody Comment comment);
}
