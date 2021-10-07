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

    @Column()
    private String keyword1_user;
    @Column()
    private String keyword2_user;
    @Column()
    private String keyword3_user;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Keywords(String keyword1_user, String keyword2_user, String keyword3_user, User user){
        this.keyword1_user = keyword1_user;
        this.keyword2_user = keyword2_user;
        this.keyword3_user = keyword3_user;
        this.user = user;
    }

    public void update(String keyword1_user, String keyword2_user, String keyword3_user){
        this.keyword1_user = keyword1_user;
        this.keyword2_user = keyword2_user;
        this.keyword3_user = keyword3_user;
    }

}
