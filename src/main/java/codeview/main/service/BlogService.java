package codeview.main.service;

import codeview.main.dto.blog.BlogReqDto;
import codeview.main.entity.Blog;
import codeview.main.entity.User;
import codeview.main.exception.BusinessException;
import codeview.main.exception.code.BlogErrorCode;
import codeview.main.exception.code.ErrorCode;
import codeview.main.exception.code.UserErrorCode;
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
    public Blog createBlog(Long userId, BlogReqDto blogReqDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
        Blog blog = new Blog(blogReqDto);
        user.addBlog(blog);
        return blogRepository.save(blog);
    }
    @Transactional
    public List<Blog> getBlogList(Long userId) {
        return blogRepository.findByUser_Id(userId);
    }
    @Transactional
    public Blog getBlog(Long blogId) {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new BusinessException(BlogErrorCode.BLOG_NOT_FOUND));
    }
    @Transactional
    public Blog updateBlog(Long userId, Long blogId, BlogReqDto blogReqDto) {
        Blog blog = blogRepository.findByIdAndUser_Id(blogId, userId)
                .orElseThrow(() -> new BusinessException(BlogErrorCode.BLOG_NOT_FOUND));
        blog.update(blogReqDto);
        return blogRepository.save(blog);
    }
    @Transactional
    public void deleteBlog(Long userId, Long blogId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
        Blog blog = blogRepository.findById(blogId).orElseThrow(()-> new BusinessException(BlogErrorCode.BLOG_NOT_FOUND));
        if (user.getId().equals(blog.getUser().getId())) {
            user.removeBlog(blog);
        } else {
            throw new BusinessException(UserErrorCode.UNAUTHORIZED_USER);
        }
    }
}
