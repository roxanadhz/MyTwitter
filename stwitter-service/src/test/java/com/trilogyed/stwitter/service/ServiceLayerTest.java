package com.trilogyed.stwitter.service;

import com.trilogyed.stwitter.model.Comment;
import com.trilogyed.stwitter.model.Post;
import com.trilogyed.stwitter.model.SwitterViewModel;
import com.trilogyed.stwitter.util.CommentService;
import com.trilogyed.stwitter.util.feign.CommentClient;
import com.trilogyed.stwitter.util.feign.PostClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    private ServiceLayer serviceLayer;

    @MockBean
    private PostClient postClient;

    @MockBean
    private CommentClient commentClient;

    @MockBean
    private CommentService commentService;



    @Before
    public void setUp() {
        setupPostMock();
        setupCommentMock();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForInvalidBookId() {
        serviceLayer.getPost(999);
    }

    private void setupCommentMock(){
        Comment toDb = new Comment();
        toDb.setPostId(1);
        toDb.setCommentContent("hello");
        toDb.setCommenterName("Alex");
        toDb.setCreateDate(LocalDate.of(2010, 10, 11));

        Comment fromDb = new Comment();
        fromDb.setCommentId(1);
        fromDb.setPostId(1);
        fromDb.setCommentContent("hello");
        fromDb.setCommenterName("Alex");
        fromDb.setCreateDate(LocalDate.of(2010, 10, 11));


        List<Comment> comments = new ArrayList<>(Collections.singletonList(fromDb));

        when(commentClient.getCommentByPostId(1)).thenReturn(comments);

    }

    private void setupPostMock() {
        Post toDb = new Post();
        toDb.setPostContent("Test Post");
        toDb.setPosterName("Kim");
        toDb.setPostDate(LocalDate.of(2010, 10, 10));


        Post fromDb = new Post();
        fromDb.setPostID(1);
        fromDb.setPostContent("Test Post");
        fromDb.setPosterName("Kim");
        fromDb.setPostDate(LocalDate.of(2010, 10, 10));

        List<Post> posts = new ArrayList<>(Collections.singletonList(fromDb));

        when(postClient.createPost(toDb)).thenReturn(fromDb);
        when(postClient.getPost(1)).thenReturn(java.util.Optional.of(fromDb));
        when(postClient.getPostByPosterName("Kim")).thenReturn(posts);
    }

    @Test
    public void shouldCreateViewModel() {
        SwitterViewModel vm = new SwitterViewModel();
        vm.setPostContent("Test Post");
        vm.setPosterName("Kim");
        vm.setPostDate(LocalDate.of(2010, 10, 10));
        Comment comment = new Comment();
        comment.setCommentContent("hello");
        comment.setCommenterName("Alex");
        comment.setCreateDate(LocalDate.of(2010, 10, 11));
        List<Comment> comments = new ArrayList<>(Collections.singletonList(comment));
        vm.setComments(comments);

        SwitterViewModel vmResult = new SwitterViewModel();
        vmResult.setId(1);
        vmResult.setPostContent("Test Post");
        vmResult.setPosterName("Kim");
        vmResult.setPostDate(LocalDate.of(2010, 10, 10));
        Comment commentResult = new Comment();

        // Comment is sent to RabbitMQ, so for this stage, the comments might not be loaded in DB (still in queue), so no ID can be set
        commentResult.setCommentId(0);
        commentResult.setPostId(1);
        commentResult.setCommentContent("hello");
        commentResult.setCommenterName("Alex");
        commentResult.setCreateDate(LocalDate.of(2010, 10, 11));
        List<Comment> commentsResult = new ArrayList<>(Collections.singletonList(commentResult));
        vmResult.setComments(commentsResult);

        SwitterViewModel s1 = serviceLayer.CreatePost(vm);
        System.out.println(s1);
        assertEquals(vmResult, s1);


    }

    @Test
    public void shouldReturnViewModelByPostId() {
        SwitterViewModel vmResult = new SwitterViewModel();
        vmResult.setId(1);
        vmResult.setPostContent("Test Post");
        vmResult.setPosterName("Kim");
        vmResult.setPostDate(LocalDate.of(2010, 10, 10));
        Comment commentResult = new Comment();
        // Comment is sent to RabbitMQ, so for this stage, the comments might not be loaded in DB (still in queue), so no ID can be set
        commentResult.setCommentId(1);
        commentResult.setPostId(1);
        commentResult.setCommentContent("hello");
        commentResult.setCommenterName("Alex");
        commentResult.setCreateDate(LocalDate.of(2010, 10, 11));
        List<Comment> commentsResult = new ArrayList<>(Collections.singletonList(commentResult));
        vmResult.setComments(commentsResult);

        assertEquals(vmResult, serviceLayer.getPost(1));
    }
}