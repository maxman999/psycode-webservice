package com.kjy.myapp.springboot.domain.keywords;

import com.kjy.myapp.springboot.domain.BaseTimeEntity;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Keywords extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String keyword1;
    @Column(length = 30)
    private String keyword2;
    @Column(length = 30)
    private String keyword3;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Keywords(String keyword1, String keyword2, String keyword3, User user){
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.user = user;
    }

    public void update(String keyword1_user, String keyword2_user, String keyword3_user){
        this.keyword1 = keyword1_user;
        this.keyword2 = keyword2_user;
        this.keyword3 = keyword3_user;
    }

}
