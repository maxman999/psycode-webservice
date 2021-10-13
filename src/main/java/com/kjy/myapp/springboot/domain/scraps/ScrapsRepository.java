package com.kjy.myapp.springboot.domain.scraps;

import com.kjy.myapp.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScrapsRepository extends JpaRepository<Scraps, Long>, QuerydslPredicateExecutor<Scraps> {
    // @Query를 이용한 sql 처리
    @Query("SELECT s FROM Scraps s ORDER BY s.id DESC")
    List<Scraps> findAllDESC();

    @Query(value = "SELECT * FROM scraps WHERE title = :title AND user_email = :user_email", nativeQuery = true)
    Scraps check(@Param("title") String title,
                 @Param("user_email") String user_email);

    Optional<User> findByUser_email(String email);
}
