package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/project-list")   // 或直接注释/删除
    public String projectList() {
        return "projects";
    }

    @GetMapping("/photos")
    public String photos() {
        return "photos";
    }

    @GetMapping("/music")
    public String music() {
        return "music";
    }

    @GetMapping("/talks")
    public String talks() {
        return "talks";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
