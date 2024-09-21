package codeview.main.dto.blog;

import codeview.main.entity.Blog;
import lombok.Getter;

@Getter
public class BlogResDto {

    private Long id;
    private String title;

    public BlogResDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
    }
}
