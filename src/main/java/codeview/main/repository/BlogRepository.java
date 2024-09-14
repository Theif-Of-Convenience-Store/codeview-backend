package codeview.main.repository;

import codeview.main.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUser_Id(Long userId);
    Optional<Blog> findByIdAndUser_Id(Long blogId, Long userId);
}
