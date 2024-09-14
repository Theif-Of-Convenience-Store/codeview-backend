package codeview.main.entity;

import codeview.main.dto.board.BoardReqDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="blog_id")
    private Blog blog;


    public Board(BoardReqDto boardReqDto) {
        this.title = boardReqDto.getTitle();
        this.content = boardReqDto.getContent();
    }
    public void addBlog(Blog blog) {
        this.blog = blog;
    }
    public void update(BoardReqDto boardReqDto) {
        this.title = boardReqDto.getTitle();
        this.content = boardReqDto.getContent();
    }
}