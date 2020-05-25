package com.trilogyed.stwitter.service;


import com.trilogyed.stwitter.model.Comment;
import com.trilogyed.stwitter.model.Post;
import com.trilogyed.stwitter.model.SwitterViewModel;
import com.trilogyed.stwitter.util.CommentService;
import com.trilogyed.stwitter.util.feign.CommentClient;
import com.trilogyed.stwitter.util.feign.PostClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceLayer {

    @Autowired
    private PostClient postClient;

    @Autowired
    private CommentClient commentClient;

    @Autowired
    private CommentService commentService;

    public SwitterViewModel CreatePost(SwitterViewModel switterViewModel) {
        Post post = new Post();
        post.setPostDate(switterViewModel.getPostDate());
        post.setPosterName(switterViewModel.getPosterName());
        post.setPostContent(switterViewModel.getPostContent());
        post = postClient.createPost(post);
        int postId = post.getPostID();
        switterViewModel.setId(postId);

        switterViewModel.getComments().forEach(comment -> comment.setPostId(postId));
        switterViewModel.getComments().forEach(comment -> commentService.processComment(comment));

        return switterViewModel;

    }

        public SwitterViewModel getPost(int id) {

          return buildViewModel(postClient.getPost(id).orElseThrow(() -> new IllegalArgumentException("Cannot find the specified post")));
    }

        public List<SwitterViewModel> getPostByPosterName (String posterName){
            return postClient.getPostByPosterName(posterName).stream().map(this::buildViewModel).collect(Collectors.toList());
        }

        private SwitterViewModel buildViewModel (Post post){
            List<Comment> comments = commentClient.getCommentByPostId(post.getPostID());
            SwitterViewModel switterViewModel = new SwitterViewModel();
            switterViewModel.setId(post.getPostID());
            switterViewModel.setPostDate(post.getPostDate());
            switterViewModel.setPosterName(post.getPosterName());
            switterViewModel.setPostContent(post.getPostContent());
            switterViewModel.setComments(comments);
            return switterViewModel;
        }
    }
