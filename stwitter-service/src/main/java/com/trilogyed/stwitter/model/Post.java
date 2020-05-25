package com.trilogyed.stwitter.model;



import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class Post {


    private int postID;
    @NotNull(message = "Post Date cannot be empty")
    private LocalDate postDate;
    @NotNull(message = "Poster Name cannot be empty")
    @Max(50)
    private String posterName;
    @Max(255)
    private String postContent;
    private List<Comment> comments;

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return getPostID() == post.getPostID() &&
                Objects.equals(getPostDate(), post.getPostDate()) &&
                Objects.equals(getPosterName(), post.getPosterName()) &&
                Objects.equals(getPostContent(), post.getPostContent()) &&
                Objects.equals(getComments(), post.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostID(), getPostDate(), getPosterName(), getPostContent(), getComments());
    }

    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", postDate=" + postDate +
                ", posterName='" + posterName + '\'' +
                ", postContent='" + postContent + '\'' +
                ", comments=" + comments +
                '}';
    }
}