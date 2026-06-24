package com.example.demo;

import com.example.demo.Article;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tag")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Article> articles;
}