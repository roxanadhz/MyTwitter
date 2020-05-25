package com.trilogyed.stwitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.trilogyed.stwitter.model.Comment;
import com.trilogyed.stwitter.model.Post;
import com.trilogyed.stwitter.model.SwitterViewModel;
import com.trilogyed.stwitter.service.ServiceLayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class StwitterControllerTest {

    @MockBean
    private ServiceLayer serviceLayer;

    @Autowired
    private StwitterController stwitterController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SwitterViewModel toDb;
    private SwitterViewModel fromDb;

    @Before
    public void setUp(){
        setupPostMock();
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setPostId(1);
        comment.setCommenterName("Alex");
        comment.setCreateDate(LocalDate.of(2019,9,13));
        comment.setCommentContent("Helpful");
        List<Comment> comments = new ArrayList<>(Collections.singletonList(comment));

        toDb = new SwitterViewModel();
        toDb.setPosterName("Kim");
        toDb.setPostDate(LocalDate.of(2018,5,12));
        toDb.setPostContent("Just finishes Harry potter");
        toDb.setComments(comments);

        fromDb = new SwitterViewModel();
        fromDb.setId(1);
        fromDb.setPosterName("Kim");
        fromDb.setPostDate(LocalDate.of(2018,5,12));
        fromDb.setPostContent("Just finishes Harry potter");
        fromDb.setComments(comments);
    }

    @Test
    public void shouldAddGetPost() throws Exception {
        String inputString = objectMapper.writeValueAsString(toDb);
        String outputString = objectMapper.writeValueAsString(fromDb);

        mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(inputString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputString));
    }
    @Test
    public void shouldReturn422ForInvalidArguments() throws Exception {
        mockMvc.perform(get("/posts/999"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        Post noName = new Post();
        noName.setPosterName("Kim");

        String inputString = objectMapper.writeValueAsString(noName);
        mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(inputString))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(put("/posts").contentType(MediaType.APPLICATION_JSON).content(inputString))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    private void setupPostMock() {
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setPostId(1);
        comment.setCommenterName("Alex");
        comment.setCreateDate(LocalDate.of(2019,9,13));
        comment.setCommentContent("Helpful");
        List<Comment> comments = new ArrayList<>(Collections.singletonList(comment));

        SwitterViewModel toDb = new SwitterViewModel();
        toDb.setPosterName("Kim");
        toDb.setPostDate(LocalDate.of(2018,5,12));
        toDb.setPostContent("Just finishes Harry potter");
        toDb.setComments(comments);

        SwitterViewModel fromDb = new SwitterViewModel();
        fromDb.setId(1);
        fromDb.setPosterName("Kim");
        fromDb.setPostDate(LocalDate.of(2018,5,12));
        fromDb.setPostContent("Just finishes Harry potter");
        fromDb.setComments(comments);

        List<SwitterViewModel> posts = new ArrayList<>(Collections.singletonList(fromDb));

        when(serviceLayer.CreatePost(toDb)).thenReturn(fromDb);
        when(serviceLayer.getPost(1)).thenReturn(fromDb);
        when(serviceLayer.getPostByPosterName("Kim")).thenReturn(posts);
        when(serviceLayer.getPost(999)).thenThrow(new IllegalArgumentException("Cannot find the specified post."));
    }

}