package com.kjy.myapp.springboot.domain.scraps;

import com.kjy.myapp.springboot.domain.BaseTimeEntity;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Scraps extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String title; // news title

    @Column(columnDefinition = "Text", nullable = false)
    private String description; // news description

    @Column(length=150, nullable = false)
    private  String originallink; // news link

    @Column(length = 20 ,nullable = false)
    private  String pubdate; // news pubdate

    @Column(columnDefinition = "Text", nullable = true)
    private String summary; // news summary created by author

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; //author

    @Builder
    public Scraps(String title, String description, String originallink, String pubdate, String summary , User user){
        this.title = title;
        this.description = description;
        this.originallink = originallink;
        this.pubdate = pubdate;
        this.summary = summary;
        this.user = user;
    }

    public void update(String title, String description){
        this.title = title;
        this.description = description;
    }

}
