package com.trilogyed.stwitter.util;

import com.trilogyed.stwitter.model.Comment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private static final String EXCHANGE = "comment-exchange";
    private static final String ROUTING_KEY = "comment.controller";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void processComment(Comment comment){
        System.out.println("sending message...");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, comment);
        System.out.println("Message Sent");
    }
}
