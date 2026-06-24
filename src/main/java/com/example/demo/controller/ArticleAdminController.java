package com.example.demo.controller;

import com.example.demo.Article;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/articles")
public class ArticleAdminController {

    @Autowired
    private ArticleService articleService;

    // 文章管理主页（支持分页和搜索）
    @GetMapping
    public String listArticles(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) String keyword,
                               Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Page<Article> articlePage;
        if (keyword != null && !keyword.isEmpty()) {
            articlePage = articleService.searchByKeywordPaged(keyword, pageable);
            model.addAttribute("keyword", keyword);
        } else {
            articlePage = articleService.findAllPaged(pageable);
        }
        model.addAttribute("articles", articlePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articlePage.getTotalPages());
        return "admin/articles";
    }

    // 修改文章状态（发布/撤回）
    @GetMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Integer id) {
        articleService.toggleStatus(id);
        return "redirect:/admin/articles";
    }

    // 删除文章
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return "redirect:/admin/articles";
    }
}