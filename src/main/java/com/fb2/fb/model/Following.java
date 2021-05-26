package com.fb2.fb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User user2;

//    public Following() {
//    }

//    public Following(User from, User to) {
//        this.from = from;
//        this.to = to;
//    }

}
