package com.trilogyed.post.dao;

import com.trilogyed.post.model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostDaoTest {

    @Autowired
    PostRepository postRepo;

    private Post post;


    @Before
    public void setUp() {

        postRepo.findAll().forEach(post1 -> postRepo.deleteById(post1.getPostID()));
        post = new Post();
        post.setPosterName("Kim");
        post.setPostContent("Global Warming");
        post.setPostDate(LocalDate.of(2017, 5, 14));
    }

    @Test
    public void shouldAddGetDeletePost(){
        post = postRepo.save(post);
        Post post2;
        post2 = postRepo.findById(post.getPostID()).orElse(null);
        assertEquals(post2,post);
        postRepo.deleteById(post.getPostID());
        assertNull(postRepo.findById(post.getPostID()).orElse(null));
    }

    @Test
    public void shouldGetAllPosts(){

        post = postRepo.save(post);
        List<Post> posts = Arrays.asList(post);
        assertEquals(posts,postRepo.findAll());
    }

    @Test
    public void shouldUpdatePost(){

        post = postRepo.save(post);
        post.setPosterName("Alex");
        postRepo.save(post);

        assertEquals(post, postRepo.findById(post.getPostID()).orElse(null));

    }

    @Test
    public void getPostByPosterName() {

        post = postRepo.save(post);
        List<Post> post2;
        post2 = postRepo.findByPosterName(post.getPosterName());
        assertEquals(Arrays.asList(post),post2);
    }


}