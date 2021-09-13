package com.kjy.myapp.springboot.domain.reply;

import com.kjy.myapp.springboot.domain.BaseTimeEntity;
import com.kjy.myapp.springboot.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String replyer;

    @ManyToOne
    private Posts post;
}
