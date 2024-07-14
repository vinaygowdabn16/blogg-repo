package com.blogapi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="body", nullable = false)
    private String body;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private Post post;
}
