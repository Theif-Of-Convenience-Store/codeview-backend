package codeview.main.service;

import codeview.main.dto.blog.BlogReqDto;
import codeview.main.entity.Blog;
import codeview.main.entity.User;
import codeview.main.repository.BlogRepository;
import codeview.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Transactional
    public Blog createBlog(String userEmail, BlogReqDto blogReqDto) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Blog blog = Blog.builder()
                .title(blogReqDto.getTitle())
                .build();
        blog.setUser(user);
        return blogRepository.save(blog);
    }

    @Transactional(readOnly = true)
    public List<Blog> getBlogList(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        return blogRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public Blog getBlog(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow();
    }

    @Transactional
    public Blog updateBlog(String userEmail, Long blogId, BlogReqDto blogReqDto) {
        Blog blog = getBlog(blogId);
        blog.setTitle(blogReqDto.getTitle());
        return blogRepository.save(blog);
    }

    @Transactional
    public void deleteBlog(String userEmail, Long blogId) {
        Blog blog = getBlog(blogId);
        blogRepository.delete(blog);
    }
}
