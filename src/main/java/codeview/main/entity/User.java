package codeview.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    private String logo;

    private String intro;

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blog> blogs = new ArrayList<>();

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    @PrePersist
    private void setDefaultRole() {
        if (this.role == null) {
            this.role = Role.ROLE_USER;
        }
    }

    public void addBlog(Blog blog) {
        blogs.add(blog);
        blog.setUser(this);
    }

    public void removeBlog(Blog blog) {
        blogs.remove(blog);
        blog.setUser(null);
    }
}
