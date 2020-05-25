package com.trilogyed.comment.dao;

import com.trilogyed.comment.model.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentDaoTest {

    @Autowired
    CommentRepository commentRepo;

    private Comment comment;

    @Before
    public void setUp(){
        comment = new Comment();
        comment.setPostId(1);
        comment.setCommentContent("This is a good one");
        comment.setCommenterName("Alex");
        comment.setCreateDate(LocalDate.of(2019,9,23));
        clearDatabase();
    }

    @Test
    public void shouldAddGetDeleteComment(){
        comment = commentRepo.save(comment);
        Comment comment2;
        comment2 = commentRepo.findById(comment.getCommentId()).orElse(null);
        assertEquals(comment2,comment);
        commentRepo.deleteById(comment.getCommentId());
        assertNull(commentRepo.findById(comment.getCommentId()).orElse(null));
    }

    @Test
    public void shouldGetAllComments(){
        comment = commentRepo.save(comment);
        Comment comment2 = new Comment();
        comment2.setPostId(2);
        comment2.setCommentContent("Like your post");
        comment2.setCommenterName("Albert");
        comment2.setCreateDate(LocalDate.of(2018,6,23));
        commentRepo.save(comment2);
        List<Comment> commentList = commentRepo.findAll();
        assertEquals(2,commentList.size());

    }

    @Test
    public void shouldUpdateComment(){
        comment = commentRepo.save(comment);
        Comment comment2 = new Comment();
        comment2.setPostId(2);
        comment2.setCommentContent("Changed my mind about your post");
        comment2.setCommenterName("Albert");
        comment2.setCreateDate(LocalDate.of(2018,6,23));
        comment2.setCommentId(comment.getCommentId());
        commentRepo.save(comment2);

        assertEquals(comment2,commentRepo.findById(comment.getCommentId()).orElse(null));

    }

    public void clearDatabase(){
        List<Comment> commentList = commentRepo.findAll();
        for(Comment comment : commentList){
            commentRepo.deleteById(comment.getCommentId());
        }
    }
}
