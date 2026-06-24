package com.example.demo.service;

import com.example.demo.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.Tag;
import com.example.demo.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<Article> getAllArticles() {
        return articleRepository.findAllByOrderByCreateTimeDesc();
    }

    public Article getArticleById(Integer id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

    public List<Article> findByTag(String tagName) {
        return articleRepository.findByTagNames(List.of(tagName));
    }

    // 可选：根据关键字搜索（需要先在 Repository 中定义方法）
    public List<Article> searchByKeyword(String keyword) {
        return articleRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    public void saveArticle(Article article, List<Integer> tagIds) {
        // 先保存文章（获得 id）
        if (tagIds != null && !tagIds.isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(tagIds);
            article.setTags(tags);
        }
        articleRepository.save(article);
    }

    public List<Article> findDrafts() {
        return articleRepository.findByStatus("DRAFT");
    }

    // 分页查询所有文章
    public Page<Article> findAllPaged(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    // 分页搜索（标题或内容）
    public Page<Article> searchByKeywordPaged(String keyword, Pageable pageable) {
        return articleRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }

    // 切换文章状态（PUBLISHED ↔ DRAFT）
    public void toggleStatus(Integer id) {
        Article article = getArticleById(id);
        if (article != null) {
            if ("PUBLISHED".equals(article.getStatus())) {
                article.setStatus("DRAFT");
            } else {
                article.setStatus("PUBLISHED");
            }
            articleRepository.save(article);
        }
    }


}