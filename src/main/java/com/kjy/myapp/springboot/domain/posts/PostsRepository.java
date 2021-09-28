package com.kjy.myapp.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long>, QuerydslPredicateExecutor<Posts> {
    // @Query를 이용한 sql 처리
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDESC();

    @Query("SELECT p FROM Posts p WHERE p.title = :title ")
    Posts check(@Param("title") String title);

}
