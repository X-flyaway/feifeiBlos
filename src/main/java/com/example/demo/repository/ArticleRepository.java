package com.example.demo.repository;

import com.example.demo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    // 原有方法...
    List<Article> findAllByOrderByCreateTimeDesc();
    @Query("SELECT DISTINCT a FROM Article a JOIN a.tags t WHERE t.name IN :tagNames")
    List<Article> findByTagNames(@Param("tagNames") List<String> tagNames);

    List<Article> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
    List<Article> findByStatus(String status);

    Page<Article> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}