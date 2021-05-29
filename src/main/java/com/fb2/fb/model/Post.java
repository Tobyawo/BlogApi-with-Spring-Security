package com.fb2.fb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "posts")
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "users_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String createdAt;



    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> likes;

    public Post(long id, String createdAt, String title, String message) {
   this.id =id;
   this.createdAt=createdAt;
   this.title=title;
   this.message=message;
    }


    public Post(Long id, User user, String title, String message, String createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

