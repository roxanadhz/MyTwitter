package com.company.commentqueueservice.util;

import com.company.commentqueueservice.CommentQueueServiceApplication;
import com.company.commentqueueservice.controller.CommentServiceClient;
import com.company.commentqueueservice.model.Comment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageListener {

    @Autowired
    private CommentServiceClient commentServiceClient;

    @RabbitListener(queues = CommentQueueServiceApplication.QUEUE_NAME)
    public void processComment(Comment comment) {

        commentServiceClient.createComment(comment);
        System.out.println(comment.toString());
    }
}
