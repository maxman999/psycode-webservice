package com.kjy.myapp.springboot.domain.keywords;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordsRepository extends JpaRepository<Keywords,Long> {
    Optional<Keywords> findById(Long id);
    Optional<Keywords> findByUser_email(String email);

}
