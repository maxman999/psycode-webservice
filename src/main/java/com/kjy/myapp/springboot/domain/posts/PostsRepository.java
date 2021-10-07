package com.kjy.myapp.springboot.domain.posts;

import com.kjy.myapp.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long>, QuerydslPredicateExecutor<Posts> {
    // @Query를 이용한 sql 처리
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDESC();

    @Query(value = "SELECT * FROM posts WHERE title = :title AND user_email = :user_email", nativeQuery = true)
    Posts check(@Param("title") String title,
                @Param("user_email") String user_email);

    Optional<User> findByUser_email(String email);
}
