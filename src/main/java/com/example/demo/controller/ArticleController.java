package com.example.demo.controller;

import com.example.demo.Article;      // 注意：需要导入 entity 包下的 Article
import com.example.demo.Tag;          // 导入实体 Tag
import com.example.demo.service.ArticleService;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;   // 移到了类字段区

    // 保存文章（新增或更新）
    @PostMapping("/article/save")
    public String saveArticle(@ModelAttribute Article article,
                              @RequestParam(required = false) List<Integer> tagIds,
                              @RequestParam(required = false) String saveAsDraft) {
        if (saveAsDraft != null) {
            article.setStatus("DRAFT");
        } else {
            article.setStatus("PUBLISHED");
        }
        articleService.saveArticle(article, tagIds);
        return "redirect:/projects";   // 改：重定向到文章列表页
    }

    // 删除文章
    @GetMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return "redirect:/projects";   // 改：重定向到文章列表页
    }

    // 新的首页（欢迎页）
    @GetMapping("/")
    public String welcome() {
        return "welcome";   // 需要创建 welcome.html 模板
    }

    // 文章列表页（原首页内容，现映射到 /projects）
    @GetMapping("/projects")
    public String articleList(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) String tag,
                              Model model) {
        List<Article> articles;
        List<Tag> allTags = tagService.findAll();
        if (keyword != null && !keyword.isEmpty()) {
            articles = articleService.searchByKeyword(keyword);
        } else if (tag != null && !tag.isEmpty()) {
            articles = articleService.findByTag(tag);
        } else {
            articles = articleService.getAllArticles();
        }
        model.addAttribute("articles", articles);
        model.addAttribute("tags", allTags);
        model.addAttribute("selectedTag", tag);
        return "index";   // 复用原来的 index.html 作为文章列表模板
    }

    // 发布新文章表单
    @GetMapping("/article/new")
    public String newArticleForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("allTags", tagService.findAll());
        return "article-form";
    }

    // 编辑文章表单
    @GetMapping("/article/edit/{id}")
    public String editArticleForm(@PathVariable Integer id, Model model) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        model.addAttribute("allTags", tagService.findAll());
        return "article-form";
    }

    // 草稿箱
    @GetMapping("/drafts")
    public String drafts(Model model) {
        List<Article> drafts = articleService.findDrafts();
        model.addAttribute("articles", drafts);
        return "drafts";
    }

    // 文章详情页
    @GetMapping("/article/{id}")
    public String viewArticle(@PathVariable Integer id, Model model) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return "redirect:/projects";   // 改：重定向到文章列表页
        }
        model.addAttribute("article", article);
        return "article-detail";
    }

}