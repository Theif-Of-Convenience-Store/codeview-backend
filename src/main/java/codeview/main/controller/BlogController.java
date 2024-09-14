package codeview.main.controller;


import codeview.main.dto.blog.BlogReqDto;
import codeview.main.dto.blog.BlogResDto;
import codeview.main.global.ApiResponse;
import codeview.main.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;

    @PostMapping
    public ApiResponse<BlogResDto> createBlog(Long userId, BlogReqDto blogReqDto){
        BlogResDto blogResDto = new BlogResDto(blogService.createBlog(userId, blogReqDto));
        return ApiResponse.createResponse("S100", "SUCCESS", blogResDto);
    }

    @GetMapping
    public ApiResponse<List<BlogResDto>> getBlogList(Long userId) {
        List<BlogResDto> blogResDtoList = blogService.getBlogList(userId).stream().map(BlogResDto::new).toList();
        return ApiResponse.createResponse("S101", "SUCCESS", blogResDtoList);
    }

    @GetMapping("/{id}")
    public ApiResponse<BlogResDto> getBlog(@PathVariable Long id) {
        BlogResDto blogResDto = new BlogResDto(blogService.getBlog(id));
        return ApiResponse.createResponse("S101", "SUCCESS", blogResDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<BlogResDto> updateBlog(Long userId, @PathVariable Long id, BlogReqDto blogReqDto) {
        BlogResDto blogResDto = new BlogResDto(blogService.updateBlog(userId, id, blogReqDto));
        return ApiResponse.createResponse("S102", "SUCCESS", blogResDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBlog(Long userId, @PathVariable Long id) {
        blogService.deleteBlog(userId, id);
        return ApiResponse.createResponse("S103", "SUCCESS", null);
    }
}
