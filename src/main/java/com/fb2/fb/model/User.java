package com.fb2.fb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;


    @ApiModelProperty(notes="first name of the user that shows for Api documentation")
    private String firstName;

    @ApiModelProperty(notes="last name of the user that shows for Api documentation")
    private String lastName;


    @ApiModelProperty(notes="Email of the user that shows for Api documentation")
    @Column(unique = true, nullable = false)
    private String email;

//    @JsonIgnore
    @Column(nullable = false)
    private String password;

       @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "relation",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<Following> following;



    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;




    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}



