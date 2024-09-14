package codeview.main.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blog> blogList = new ArrayList<>();

    public void addBlog(Blog blog) {
        blogList.add(blog);
        blog.addUser(this); // Blog에 User 설정
    }

    public void removeBlog(Blog blog) {
        blogList.remove(blog);
    }
}
