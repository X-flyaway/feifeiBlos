package com.example.demo.controller;

import com.example.demo.Tag;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tags")
public class TagAdminController {

    @Autowired
    private TagService tagService;

    // 标签列表页
    @GetMapping
    public String listTags(Model model) {
        model.addAttribute("tags", tagService.findAll());
        return "admin/tags";
    }

    // 新增标签表单页
    @GetMapping("/new")
    public String newTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag-form";
    }

    // 编辑标签表单页
    @GetMapping("/edit/{id}")
    public String editTagForm(@PathVariable Integer id, Model model) {
        Tag tag = tagService.findById(id);
        if (tag == null) {
            return "redirect:/admin/tags";
        }
        model.addAttribute("tag", tag);
        return "admin/tag-form";
    }

    // 保存标签（新增或更新）
    @PostMapping("/save")
    public String saveTag(@ModelAttribute Tag tag) {
        // 简单校验，可自行扩展
        if (tag.getName() == null || tag.getName().trim().isEmpty()) {
            return "redirect:/admin/tags";
        }
        tagService.save(tag);
        return "redirect:/admin/tags";
    }

    // 删除标签
    @GetMapping("/delete/{id}")
    public String deleteTag(@PathVariable Integer id) {
        tagService.deleteById(id);
        return "redirect:/admin/tags";
    }
}