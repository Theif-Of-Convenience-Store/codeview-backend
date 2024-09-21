package codeview.main.controller;

import codeview.main.dto.blog.BlogReqDto;
import codeview.main.dto.blog.BlogResDto;
import codeview.main.global.ApiResponse;
import codeview.main.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ApiResponse<BlogResDto> createBlog(Authentication authentication, @RequestBody BlogReqDto blogReqDto) {
        String userEmail = authentication.getName();
        BlogResDto blogResDto = new BlogResDto(blogService.createBlog(userEmail, blogReqDto));
        return ApiResponse.createResponse("S100", "SUCCESS", blogResDto);
    }

    @GetMapping
    public ApiResponse<List<BlogResDto>> getBlogList(Authentication authentication) {
        String userEmail = authentication.getName();
        List<BlogResDto> blogResDtoList = blogService.getBlogList(userEmail).stream().map(BlogResDto::new).toList();
        return ApiResponse.createResponse("S101", "SUCCESS", blogResDtoList);
    }

    @GetMapping("/{id}")
    public ApiResponse<BlogResDto> getBlog(@PathVariable Long id) {
        BlogResDto blogResDto = new BlogResDto(blogService.getBlog(id));
        return ApiResponse.createResponse("S101", "SUCCESS", blogResDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<BlogResDto> updateBlog(Authentication authentication, @PathVariable Long id, @RequestBody BlogReqDto blogReqDto) {
        String userEmail = authentication.getName();
        BlogResDto blogResDto = new BlogResDto(blogService.updateBlog(userEmail, id, blogReqDto));
        return ApiResponse.createResponse("S102", "SUCCESS", blogResDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBlog(Authentication authentication, @PathVariable Long id) {
        String userEmail = authentication.getName();
        blogService.deleteBlog(userEmail, id);
        return ApiResponse.createResponse("S103", "SUCCESS", null);
    }
}
