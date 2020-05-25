package com.trilogyed.stwitter.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class
SwitterViewModel {

    private int id;
    @NotNull
    private LocalDate postDate;
    @NotNull
    @Size(max=50, message = "Poster name should not be greater than 50")
    private String posterName;
    @Size(max=255, message = "Post Content should not be greater than 255")
    private String postContent;
    private List<Comment> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        SwitterViewModel that = (SwitterViewModel) o;
        return getId() == that.getId() &&
                Objects.equals(getPostDate(), that.getPostDate()) &&
                Objects.equals(getPosterName(), that.getPosterName()) &&
                Objects.equals(getPostContent(), that.getPostContent()) &&
                Objects.equals(getComments(), that.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPostDate(), getPosterName(), getPostContent(), getComments());
    }

    @Override
    public String toString() {
        return "SwitterViewModel{" +
                "id=" + id +
                ", postDate=" + postDate +
                ", posterName='" + posterName + '\'' +
                ", postContent='" + postContent + '\'' +
                ", comments=" + comments +
                '}';
    }
}
