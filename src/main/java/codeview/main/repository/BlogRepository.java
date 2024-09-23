package codeview.main.repository;

import codeview.main.entity.Blog;
import codeview.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUser(User user);
}
