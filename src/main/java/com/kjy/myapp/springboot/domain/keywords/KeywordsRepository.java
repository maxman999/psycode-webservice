package com.kjy.myapp.springboot.domain.keywords;

import com.kjy.myapp.springboot.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KeywordsRepository extends JpaRepository<Keywords,Long> {
    Optional<Keywords> findById(Long id);

}
