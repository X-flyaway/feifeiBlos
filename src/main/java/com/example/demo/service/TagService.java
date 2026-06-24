package com.example.demo.service;

import com.example.demo.Tag;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    // 查询所有标签
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    // 根据ID查询标签
    public Tag findById(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    // 根据名称查询标签
    public Tag findByName(String name) {
        return tagRepository.findByName(name).orElse(null);
    }

    // 保存标签
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    // 删除标签
    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }
}